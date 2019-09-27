package com.leory.badminton.news.mvp.presenter

import android.text.TextUtils
import com.leory.badminton.news.app.utils.FileHashUtils
import com.leory.badminton.news.mvp.contract.HandOffRecordContract
import com.leory.badminton.news.mvp.model.bean.HandOffBean
import com.leory.commonlib.di.scope.ActivityScope
import com.leory.commonlib.mvp.BasePresenter
import com.leory.commonlib.utils.LogUtils
import com.leory.commonlib.utils.RxLifecycleUtils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * Describe : 交手记录presenter
 * Author : leory
 * Date : 2019-07-23
 */
@ActivityScope
class HandOffRecordPresenter @Inject constructor(model: HandOffRecordContract.Model, rootView: HandOffRecordContract.View, private val handOffUrl: String) :
        BasePresenter<HandOffRecordContract.Model, HandOffRecordContract.View>(model, rootView) {
    private val matchNameMap: HashMap<String, String> by lazy {
        FileHashUtils.matchName
    }
    private val playerNameMap: HashMap<String, String> by lazy {
        FileHashUtils.playerName
    }

    init {
        requestData()
        LogUtils.d(TAG, handOffUrl)
    }

    private fun requestData() {
        model.getHandOffRecords(handOffUrl)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { rootView.showLoading() }.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { rootView.hideLoading() }
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(s: String) {

                    }

                    override fun onError(e: Throwable) {
                        if (e is HttpException) {
                            if (e.code() == 404) {
                                try {
                                    val response = e.response().errorBody()!!.string()
                                    parseHtmlResult(response)
                                } catch (e1: IOException) {
                                    e1.printStackTrace()
                                }

                            }
                        }
                    }

                    override fun onComplete() {

                    }
                })
    }

    private fun parseHtmlResult(html: String) {
        Observable.just(html)

                .flatMap { Observable.just(getHandOffData(html)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(object : Observer<HandOffBean> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(bean: HandOffBean) {
                        rootView.showHandOffView(bean)
                    }

                    override fun onError(e: Throwable) {
                        LogUtils.d(TAG, e.message)
                    }

                    override fun onComplete() {

                    }
                })
    }

    private fun getHandOffData(html: String?): HandOffBean {
        val bean = HandOffBean()
        if (html != null) {
            val doc = Jsoup.parse(html)
            val headElement = doc.select("div.head-to-head").first()
            val player1s = headElement.select("div.info-player1")
            if (player1s != null) {
                if (player1s.size > 0) {
                    bean.player1HeadUrl = player1s[0].select("div.img-player img").attr("src")
                    bean.player1Flag = player1s[0].select("div.flag img").attr("src")
                    bean.player1Name = translatePlayerName(player1s[0].select("div.name a").text())
                }
                if (player1s.size > 1) {
                    bean.player12HeadUrl = player1s[1].select("div.img-player img").attr("src")
                    bean.player12Flag = player1s[1].select("div.flag img").attr("src")
                    bean.player12Name = translatePlayerName(player1s[1].select("div.name a").text())
                }
            }
            val player2s = headElement.select("div.info-player2")
            if (player2s != null) {
                if (player2s.size > 0) {
                    bean.player2HeadUrl = player2s[0].select("div.img-player img").attr("src")
                    bean.player2Flag = player2s[0].select("div.flag img").attr("src")
                    bean.player2Name = translatePlayerName(player2s[0].select("div.name a").text())
                }
                if (player2s.size > 1) {
                    bean.player22HeadUrl = player2s[1].select("div.img-player img").attr("src")
                    bean.player22Flag = player2s[1].select("div.flag img").attr("src")
                    bean.player22Name = translatePlayerName(player2s[1].select("div.name a").text())
                }
            }
            val rankings = headElement.select("div.title-rank")
            if (rankings != null && rankings.size == 2) {
                bean.player1Ranking = rankings[0].text().replace("RANKED", "排名")
                bean.player2Ranking = rankings[1].text().replace("RANKED", "排名")
            }
            val scores = headElement.select("div.h2h-score")
            if (scores != null && scores.size == 2) {
                bean.player1Win = scores[0].text()
                bean.player2Win = scores[1].text()
            }
            val records = headElement.select("div.h2h-result-row")
            if (records != null) {
                val recordList = ArrayList<HandOffBean.Record>()
                for (record in records) {
                    val recordBean = HandOffBean.Record()
                    recordBean.date = record.select("div.h2h_result_date").text()
                    recordBean.matchName = translateMatchName(record.select("div.tmt-name").text())
                    recordBean.score = record.select("span.score").text()
                    val result = record.select("div.player-result-score").text().trim { it <= ' ' }
                    recordBean.isLeftWin = result.contains("W")
                    recordList.add(recordBean)
                }
                bean.recordList = recordList
                var player1Win = 0
                var player2Win = 0
                for (record in recordList) {
                    if (record.isLeftWin) {
                        player1Win++
                    } else {
                        player2Win++
                    }
                }
                bean.player1Win = player1Win.toString()
                bean.player2Win = player2Win.toString()
            }
        }
        return bean
    }

    private fun translatePlayerName(key: String): String {

        val playerName = key.replace("\\[\\d+\\]".toRegex(), "").trim { it <= ' ' }
        val value = playerNameMap!![playerName]
        return if (TextUtils.isEmpty(value)) {
            key
        } else {
            key.replace(playerName, value!!)
        }
    }

    private fun translateMatchName(key: String): String {

        val m = Pattern.compile("\\d+").matcher(key)
        var year = ""
        if (m.find()) {
            year = m.group()
        }
        val matchNameNotYear = key.replace("\\d+".toRegex(), "").trim { it <= ' ' }
        val value = matchNameMap!![matchNameNotYear.toUpperCase()]
        return if (TextUtils.isEmpty(value)) {
            key
        } else {
            year + value!!
        }
    }
}
