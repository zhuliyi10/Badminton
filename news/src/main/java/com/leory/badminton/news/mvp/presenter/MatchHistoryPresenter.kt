package com.leory.badminton.news.mvp.presenter

import android.text.TextUtils
import com.leory.badminton.news.mvp.contract.MatchDetailContract
import com.leory.badminton.news.mvp.model.MatchDetailModel
import com.leory.badminton.news.mvp.model.bean.MatchHistoryBean
import com.leory.badminton.news.mvp.model.bean.MatchHistoryHeadBean
import com.leory.badminton.news.mvp.model.bean.MultiMatchHistoryBean
import com.leory.commonlib.di.scope.FragmentScope
import com.leory.commonlib.http.RxHandlerSubscriber
import com.leory.commonlib.mvp.BasePresenter
import com.leory.commonlib.utils.RxLifecycleUtils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import java.util.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Describe : 历史赛事presenter
 * Author : leory
 * Date : 2019-06-07
 */
@FragmentScope
class MatchHistoryPresenter @Inject constructor(@Named("history_url") var historyUrl: String,
                                                @Named("player_name") var playerNameMap: HashMap<String, String>,
                                                model: MatchDetailModel,
                                                rootView: MatchDetailContract.MatchHistory) :
        BasePresenter<MatchDetailModel, MatchDetailContract.MatchHistory>(model, rootView) {

    fun requestData() {
        model.getMatchHistory(historyUrl)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { rootView.showLoading() }.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { rootView.hideLoading() }
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(object : RxHandlerSubscriber<String>() {

                    override fun onNext(o: String) {
                        parseHtmlResult(o)
                    }

                })
    }

    private fun parseHtmlResult(html: String) {
        Observable.just(html)

                .flatMap { Observable.just(getMatchHistoryData(html)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose<List<MultiMatchHistoryBean<*>>>(RxLifecycleUtils.bindToLifecycle<List<MultiMatchHistoryBean<*>>>(rootView))
                .subscribe(object : Observer<List<MultiMatchHistoryBean<*>>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(data: List<MultiMatchHistoryBean<*>>) {
                        rootView.showHistoryData(data)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
    }

    private fun getMatchHistoryData(html: String?): List<MultiMatchHistoryBean<*>> {
        val data = ArrayList<MultiMatchHistoryBean<*>>()
        if (html != null) {
            val doc = Jsoup.parse(html)
            val box = doc.select("div.box-historical")
            if (box != null) {
                for (item in box) {
                    val headBean = MatchHistoryHeadBean()
                    headBean.year = item.select("h3").first().text()
                    headBean.matchName = item.select("h5").first().text()
                    headBean.detailUrl = item.select("a").first().attr("href")
                    data.add(MultiMatchHistoryBean(MultiMatchHistoryBean.TYPE_HEAD, headBean))

                    val list = item.select("div#list-historical >div")
                    if (list != null && list.size == 5) {
                        var info = list[0]
                        val historyBean = MatchHistoryBean()
                        historyBean.msHead = info.select("div.image img").first().attr("src")
                        historyBean.msFlag = info.select("div.description img").first().attr("src")
                        historyBean.msName = translatePlayerName(info.select("div.description a").first().text())
                        info = list[1]
                        historyBean.wsHead = info.select("div.image img").first().attr("src")
                        historyBean.wsFlag = info.select("div.description img").first().attr("src")
                        historyBean.wsName = translatePlayerName(info.select("div.description a").first().text())
                        info = list[2]
                        historyBean.md1Head = info.select("div.item-double-1 div.image img").first().attr("src")
                        historyBean.md1Flag = info.select("div.item-double-1 div.description img").first().attr("src")
                        historyBean.md1Name = translatePlayerName(info.select("div.item-double-1 div.description a").first().text())
                        historyBean.md2Head = info.select("div.item-double-2 div.image img").first().attr("src")
                        historyBean.md2Flag = info.select("div.item-double-2 div.description img").first().attr("src")
                        historyBean.md2Name = translatePlayerName(info.select("div.item-double-2 div.description a").first().text())
                        info = list[3]
                        historyBean.wd1Head = info.select("div.item-double-1 div.image img").first().attr("src")
                        historyBean.wd1Flag = info.select("div.item-double-1 div.description img").first().attr("src")
                        historyBean.wd1Name = translatePlayerName(info.select("div.item-double-1 div.description a").first().text())
                        historyBean.wd2Head = info.select("div.item-double-2 div.image img").first().attr("src")
                        historyBean.wd2Flag = info.select("div.item-double-2 div.description img").first().attr("src")
                        historyBean.wd2Name = translatePlayerName(info.select("div.item-double-2 div.description a").first().text())
                        info = list[4]
                        historyBean.xd1Head = info.select("div.item-double-1 div.image img").first().attr("src")
                        historyBean.xd1Flag = info.select("div.item-double-1 div.description img").first().attr("src")
                        historyBean.xd1Name = translatePlayerName(info.select("div.item-double-1 div.description a").first().text())
                        historyBean.xd2Head = info.select("div.item-double-2 div.image img").first().attr("src")
                        historyBean.xd2Flag = info.select("div.item-double-2 div.description img").first().attr("src")
                        historyBean.xd2Name = translatePlayerName(info.select("div.item-double-2 div.description a").first().text())
                        data.add(MultiMatchHistoryBean(MultiMatchHistoryBean.TYPE_CONTENT, historyBean))
                    }

                }
            }
        }

        return data
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
}
