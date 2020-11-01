package com.leory.badminton.news.mvp.presenter

import android.text.TextUtils
import com.leory.badminton.news.mvp.contract.MatchDetailContract
import com.leory.badminton.news.mvp.model.bean.MatchPlayerBean
import com.leory.badminton.news.mvp.model.bean.MatchPlayerHeadBean
import com.leory.badminton.news.mvp.model.bean.MatchPlayerListBean
import com.leory.badminton.news.mvp.model.bean.MultiMatchPlayersBean
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
import org.jsoup.select.Elements
import java.util.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Describe : 参赛运动员 presenter
 * Author : leory
 * Date : 2019-06-10
 */
@FragmentScope
class MatchPlayersPresenter @Inject constructor(model: MatchDetailContract.Model,
                                                @Named("player_name")
                                                var playerNameMap: HashMap<String, String>,
                                                @Named("country_name")
                                                var countryNameMap: HashMap<String, String>,
                                                @Named("detail_url")
                                                var detailUrl: String,
                                                rootView: MatchDetailContract.MatchPlayersView
) :
        BasePresenter<MatchDetailContract.Model, MatchDetailContract.MatchPlayersView>(model, rootView) {


    fun requestData(type: String) {
        if (detailUrl != null) {
            val requestUrl = detailUrl + "players/"
            val tab = getTab(type)
            model.getMatchPlayers(requestUrl, tab)
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

                .flatMap { Observable.just(getMatchPlayersData(html)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose<List<MultiMatchPlayersBean<*>>>(RxLifecycleUtils.bindToLifecycle<List<MultiMatchPlayersBean<*>>>(rootView))
                .subscribe(object : Observer<List<MultiMatchPlayersBean<*>>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(data: List<MultiMatchPlayersBean<*>>) {
                        rootView.showPlayersData(data)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
    }

    private fun getMatchPlayersData(html: String?): List<MultiMatchPlayersBean<*>> {
        val data = ArrayList<MultiMatchPlayersBean<*>>()
        if (html != null) {
            val doc = Jsoup.parse(html)
            val classifies = doc.select("div.rankings-content_tabpanel")
            if (classifies != null && classifies.size == 2) {
                var headBean = MatchPlayerHeadBean()
                //                headBean.setName(classifies.get(0).select("h3").first().text());
                headBean.name = "种子选手"
                data.add(MultiMatchPlayersBean(MultiMatchPlayersBean.TYPE_HEAD, headBean))

                var listBean = MatchPlayerListBean()
                listBean.data = getPlayerList(classifies[0].select("div.entry-player-pair-wrap"))
                data.add(MultiMatchPlayersBean(MultiMatchPlayersBean.TYPE_CONTENT, listBean))

                headBean = MatchPlayerHeadBean()
                //                headBean.setName(classifies.get(1).select("h3").first().text());
                //                headBean.setSecond(classifies.get(1).select("h5").first().text());
                headBean.name = "所有运动员"
                data.add(MultiMatchPlayersBean(MultiMatchPlayersBean.TYPE_HEAD, headBean))
                val countries = classifies[1].select("div.entry-player-country")
                for (country in countries) {
                    listBean = MatchPlayerListBean()
                    listBean.data = getPlayerList(country.select("div.entry-player-pair-wrap"))
                    data.add(MultiMatchPlayersBean(MultiMatchPlayersBean.TYPE_CONTENT, listBean))
                }
            }
        }
        return data
    }

    private fun getPlayerList(elements: Elements?): List<MatchPlayerBean> {
        val data = ArrayList<MatchPlayerBean>()
        if (elements != null) {
            for (e in elements) {
                val players = e.select("a")
                if (players != null) {
                    val playerBean = MatchPlayerBean()
                    if (players.size > 0) {
                        val player = players[0]
                        playerBean.url1=player.attr("href")
                        playerBean.head1 = player.select("div.entry-player-image img").first().attr("src")
                        playerBean.name1 = translatePlayerName(player.select("div.entry-player-name").first().text())
                        playerBean.flag1 = player.select("div.entry-player-flag img").first().attr("src")
                        playerBean.country1 = translateCountryName(player.select("div.entry-player-flag span").first().text())
                    }
                    if (players.size > 1) {
                        val player = players[1]
                        playerBean.url2=player.attr("href")
                        playerBean.head2 = player.select("div.entry-player-image img").first().attr("src")
                        playerBean.name2 = translatePlayerName(player.select("div.entry-player-name").first().text())
                        playerBean.flag2 = player.select("div.entry-player-flag img").first().attr("src")
                        playerBean.country2 = translateCountryName(player.select("div.entry-player-flag span").first().text())
                    }
                    data.add(playerBean)
                }
            }
        }
        return data
    }

    private fun getTab(type: String): String {
        when (type) {
            "男单" -> return "ms"
            "女单" -> return "ws"
            "男双" -> return "md"
            "女双" -> return "wd"
            "混双" -> return "xd"
            else -> return "ms"
        }
    }

    private fun translatePlayerName(key: String): String {
        val playerName = key.replace("\\[\\d+\\]".toRegex(), "").trim { it <= ' ' }
        val value = playerNameMap[playerName]
        return if (TextUtils.isEmpty(value)) {
            key
        } else {
            key.replace(playerName, value!!)
        }
    }

    private fun translateCountryName(key: String): String {
        val value = countryNameMap[key]
        return if (TextUtils.isEmpty(value)) {
            key
        } else {
            key.replace(key, value!!)
        }
    }
}
