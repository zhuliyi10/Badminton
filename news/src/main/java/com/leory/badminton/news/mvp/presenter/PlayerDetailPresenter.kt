package com.leory.badminton.news.mvp.presenter

import android.text.TextUtils
import com.leory.badminton.news.app.utils.FileHashUtils
import com.leory.badminton.news.mvp.contract.PlayerContract
import com.leory.badminton.news.mvp.model.bean.PlayerDetailBean
import com.leory.badminton.news.mvp.model.bean.PlayerInfoBean
import com.leory.commonlib.di.scope.ActivityScope
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
 * Describe : 运动员详情presenter
 * Author : leory
 * Date : 2019-06-11
 */
@ActivityScope
class PlayerDetailPresenter @Inject constructor(model: PlayerContract.Model, rootView: PlayerContract.View, @param:Named("player_url") private val playerUrl: String?) :
        BasePresenter<PlayerContract.Model, PlayerContract.View>(model, rootView) {

    private val playerNameMap: HashMap<String, String> by lazy {
        FileHashUtils.playerName
    }

    init {
        requestData()
    }

    private fun requestData() {
        if (playerUrl != null) {
            model.getPlayerDetail(playerUrl)
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
    }

    private fun parseHtmlResult(html: String) {
        Observable.just(html)

                .flatMap { Observable.just(getPlayerDetailData(html)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(object : Observer<PlayerDetailBean> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(data: PlayerDetailBean) {
                        rootView.showPlayerDetail(data)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
    }

    private fun getPlayerDetailData(html: String?): PlayerDetailBean {
        val bean = PlayerDetailBean()
        if (html != null) {
            val doc = Jsoup.parse(html)
            val info = doc.select("div.playertop-intro").first()
            bean.head = info.select("div.player-photo img").first().attr("src")
            bean.flag = info.select("div.player-profile-country-wrap img").first().attr("src")
            bean.name = translatePlayerName(info.select("div.player-profile-country-wrap h2").first().text())
            val ranking = info.select("div.player-world-rank div.ranking-number").first()
            if (ranking != null) {
                bean.rankNum = ranking.text()
                bean.type = info.select("div.player-world-rank div.ranking-title").first().text()
            } else {
                val trs = info.select("div.player-world-rank tr")
                if (trs.size == 2) {
                    val ranks = trs[0].select("td")
                    val types = trs[1].select("td")
                    bean.rankNum = ranks[0].text()
                    bean.type = types[0].text()
                    bean.rankNum2 = ranks[1].text()
                    bean.type2 = types[1].text()
                }
            }
            bean.winNum = info.select("div.player-wins span.large").first().text()
            bean.age = info.select("div.player-age span.large").first().text()

            val stats = doc.select("div.player-stats").first()
            if (stats != null) {
                val infoBean = PlayerInfoBean()
                val pList = stats.select("p")
                val buffer = StringBuffer()
                for (p in pList) {
                    buffer.append(p.text())
                    buffer.append("\n")
                }
                infoBean.stats = buffer.toString()
                bean.infoBean = infoBean
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

}
