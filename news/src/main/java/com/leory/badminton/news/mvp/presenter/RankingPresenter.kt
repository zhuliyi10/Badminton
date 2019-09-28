package com.leory.badminton.news.mvp.presenter

import android.text.TextUtils
import com.leory.badminton.news.app.utils.FileHashUtils
import com.leory.badminton.news.mvp.contract.RankingContract
import com.leory.badminton.news.mvp.model.bean.RankingBean
import com.leory.badminton.news.mvp.ui.fragment.RankingFragment.Companion.RANKING_TYPES
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

/**
 * Describe : 排名presenter
 * Author : leory
 * Date : 2019-05-21
 */
@FragmentScope
class RankingPresenter @Inject
constructor(model: RankingContract.Model, rootView: RankingContract.View) : BasePresenter<RankingContract.Model, RankingContract.View>(model, rootView) {
    private var lastPage = 0
    private val pageNum = 25
    private val weekMap = LinkedHashMap<String, String>()
    private val countryMap: HashMap<String, String> by lazy {
        FileHashUtils.country
    }
    private val playerNameMap: HashMap<String, String> by lazy {
        FileHashUtils.playerName
    }

    /**
     * 首次执行
     */
    fun firstInit() {
        requestData(true, true, null, null)
    }

    /**
     * 选择数据
     *
     * @param rankingType
     * @param week
     */
    fun selectData(refresh: Boolean, rankingType: String, week: String) {
        var concatType: String? = null
        when (rankingType) {
            RANKING_TYPES[0] -> //男单
                concatType = "6/men-s-singles/"
            RANKING_TYPES[1] -> //女单
                concatType = "7/women-s-singles/"
            RANKING_TYPES[2] -> //男双
                concatType = "8/men-s-doubles/"
            RANKING_TYPES[3] -> //女双
                concatType = "9/women-s-doubles/"
            RANKING_TYPES[4] -> //混双
                concatType = "10/mixed-doubles/"
        }
        var concatWeek = weekMap[week]
        if (concatWeek != null) concatWeek = concatWeek.replace("--", "/")
        requestData(false, refresh, concatType, concatWeek)
    }

    /**
     * 请求数据
     *
     * @param refresh
     * @param rankingType
     * @param week
     */
    private fun requestData(first: Boolean, refresh: Boolean, rankingType: String?, week: String?) {
        if (refresh) lastPage = 0
        model.getRankingList(rankingType, week, pageNum, lastPage + 1)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { disposable ->
                    if (refresh) {
                        rootView.showLoading()//显示下拉刷新的进度条
                    } else {
                        rootView.startLoadMore()//显示上拉加载更多的进度条
                    }
                }.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    if (refresh)
                        rootView.hideLoading()//隐藏下拉刷新的进度条
                    else
                        rootView.endLoadMore()//隐藏上拉加载更多的进度条
                }
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(object : RxHandlerSubscriber<String>() {
                    override fun onNext(s: String) {
                        lastPage++
                        parseHtmlResult(first, refresh, s)
                    }
                })
    }


    /**
     * 解析html
     *
     * @param html
     */
    private fun parseHtmlResult(first: Boolean, refresh: Boolean, html: String) {

        Observable.just(html)

                .flatMap { Observable.just(getRankingData(first, html)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(object : Observer<List<RankingBean>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(matchItemSections: List<RankingBean>) {
                        rootView.showRankingData(refresh, matchItemSections)
                    }

                    override fun onError(e: Throwable) {
                        rootView.showRankingData(refresh, ArrayList())
                    }

                    override fun onComplete() {

                    }
                })
    }

    private fun getRankingData(first: Boolean, html: String?): List<RankingBean> {
        val rankingList = ArrayList<RankingBean>()
        if (html != null) {
            val doc = Jsoup.parse(html)
            if (first) {
                val weekOptions = doc.select("select#ranking-week").first().select("option")
                val week = ArrayList<String>()
                for (option in weekOptions) {
                    val value = option.attr("value")
                    val key = option.text()
                    if (!TextUtils.isEmpty(key)) {
                        weekMap[key] = value
                        week.add(key)
                    }
                }
                rootView.showWeekData(week)

            }
            val trList = doc.select("tr")
            var i = 1
            while (i < trList.size) {
                val tr = trList[i]
                val tdList = tr.select("td")
                if (tdList.size == 8) {
                    val bean = RankingBean()
                    bean.rankingNum = tdList[0].text()
                    val countries = tdList[1].select("div.country")
                    if (countries.size > 0) {
                        bean.countryName = translateCountry(countries[0].select("span").first().text())
                        bean.countryFlagUrl = countries[0].select("img").first().attr("src")
                    }
                    if (countries.size > 1) {
                        bean.country2Name = translateCountry(countries[1].select("span").first().text())
                        bean.countryFlag2Url = countries[1].select("img").first().attr("src")
                    }
                    val players = tdList[2].select("div.player span")
                    if (players.size > 0) {
                        bean.playerName = translatePlayerName(players[0].select("a").first().text())
                        bean.playerUrl = players[0].select("a").first().attr("href")
                    }
                    if (players.size > 1) {
                        bean.player2Name = translatePlayerName(players[1].select("a").first().text())
                        bean.player2Url = players[1].select("a").first().attr("href")
                    }
                    bean.riseOrDrop = Integer.parseInt(tdList[3].select("span").first().text())
                    val arrowElement = tdList[3].select("img").first()
                    if (arrowElement != null) {
                        if (arrowElement.attr("src").contains("arrow-down")) {//为负
                            bean.riseOrDrop = 0 - bean.riseOrDrop
                        }
                    }
                    bean.winAndLoss = tdList[4].text()
                    bean.bonus = tdList[5].text()
                    val point = tdList[6].select("strong").first().text()
                    if (point != null) {
                        val points = point.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        if (points.size > 0) {
                            bean.points = points[0].trim { it <= ' ' }
                        }
                    }

                    bean.playerId = tdList[7].select("div").first().attr("id")
                    rankingList.add(bean)
                }
                i += 2

            }


        }
        return rankingList
    }

    /**
     * 翻译国家
     *
     * @param key
     * @return
     */
    private fun translateCountry(key: String): String? {

        val value = countryMap[key]
        return if (TextUtils.isEmpty(value)) {
            key
        } else {
            value
        }
    }

    /**
     * 翻译运动员名字
     *
     * @param key
     * @return
     */
    private fun translatePlayerName(key: String): String? {

        val value = playerNameMap[key]
        return if (TextUtils.isEmpty(value)) {
            key
        } else {
            value
        }
    }
}
