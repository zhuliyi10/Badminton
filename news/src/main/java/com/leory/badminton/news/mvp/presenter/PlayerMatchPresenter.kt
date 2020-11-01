package com.leory.badminton.news.mvp.presenter

import android.text.TextUtils
import com.leory.badminton.news.app.utils.FileHashUtils
import com.leory.badminton.news.mvp.contract.PlayerContract
import com.leory.badminton.news.mvp.model.bean.PlayerMatchBean
import com.leory.badminton.news.mvp.ui.widget.againstFlow.AgainstFlowBean
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
import org.jsoup.nodes.Element
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.collections.ArrayList

/**
 * Describe : 运动员赛果
 * Author : leory
 * Date : 2019-06-11
 */
@FragmentScope
class PlayerMatchPresenter @Inject constructor(model: PlayerContract.Model, rootView: PlayerContract.MatchView, @param:Named("player_url") private val playerUrl: String?) :
        BasePresenter<PlayerContract.Model, PlayerContract.MatchView>(model, rootView) {

    private val matchNameMap: HashMap<String, String> by lazy {
        FileHashUtils.matchName
    }
    private val playerNameMap: HashMap<String, String> by lazy {
        FileHashUtils.playerName
    }
    private var isShort = false
    var cacheData: List<PlayerMatchBean>? = null
    fun requestData(year: String?, isShort: Boolean = false) {
        if (playerUrl != null) {
            val requestUrl = "$playerUrl/tournament-results"
            model.getPlayerMatches(requestUrl, year)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { rootView.showLoading() }.subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { rootView.hideLoading() }
                    .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                    .subscribe(object : RxHandlerSubscriber<String>() {

                        override fun onNext(o: String) {
                            parseHtmlResult(o)
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

                    })
        }
    }



    private fun parseHtmlResult(html: String) {
        Observable.just(html)
                .flatMap { Observable.just(getPlayerMatchData(html)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(object : Observer<List<PlayerMatchBean>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(data: List<PlayerMatchBean>) {
                        cacheData = data
                        showData()
                    }

                    override fun onError(e: Throwable) {
                        print(e.message)
                    }

                    override fun onComplete() {

                    }
                })
    }

    fun setIsShort(isShort: Boolean){
        this.isShort=isShort
        showData()
    }
    private fun showData() {
        cacheData?.apply {
            val data= mutableListOf<PlayerMatchBean>()
            forEach { item ->
                item.matchInfo?.isShort=isShort
                item.rounds?.forEach {
                    it.isShort=isShort
                }
                item.rounds?.run {

                    if(this[this.size - 1].round == "决赛"){
                        val newItem=PlayerMatchBean()
                        newItem.matchInfo=item.matchInfo
                        val newRound= mutableListOf<PlayerMatchBean.ResultRound>()
                        newRound.add(this[this.size - 1])
                        newItem.rounds=newRound
                        data.add(newItem)
                    }
                }
            }
            if (isShort) {
                rootView.showMatchData(data)
            } else {
                rootView.showMatchData(this)
            }

        }

    }

    private fun getPlayerMatchData(html: String?): List<PlayerMatchBean> {
        val data = ArrayList<PlayerMatchBean>()
        if (html != null) {
            val doc = Jsoup.parse(html)
            val matches = mutableListOf<Element>()
            val rounds = ArrayList<List<Element>>()
            val target = doc.select("div.tournament-results > div")
            for (index in target.indices) {
                if (target[index].attr("class") == "box-profile-tournament") {
                    matches.add(target[index])
                    val round=ArrayList<Element>()
                    if(target[index+2].attr("class") == "tournament-matches"){
                        val rows=target[index+2].select("div.col-1-2 div.tournament-matches-row")
                        for (row in rows){
                            round.add(row)
                        }
                    }
                    if(index+4<target.size) {
                        if (target[index + 4].attr("class") == "tournament-matches") {
                            val rows = target[index + 4].select("div.col-1-2 div.tournament-matches-row")
                            for (row in rows) {
                                round.add(row)
                            }
                        }
                    }
                    rounds.add(round)
                }
            }
            for (i in matches.indices) {
                val matchBean = PlayerMatchBean()
                val match = matches[i]
                val matchInfo = PlayerMatchBean.MatchInfo()
                matchInfo.matchUrl = match.select("h2 a").first().attr("href")
                matchInfo.name = translateMatchName(match.select("h2 a").first().text())
                matchInfo.category = match.select("h3").first().text()
                matchInfo.date = match.select("h4").first().text()
                val bonus = match.select("div.prize").first()
                if (bonus != null) {
                    matchInfo.bonus = bonus.text()
                }
                matchBean.matchInfo = matchInfo
                val round = rounds[i]
                val roundList = ArrayList<PlayerMatchBean.ResultRound>()
                for (row in round) {
                    val resultRound = PlayerMatchBean.ResultRound()
                    resultRound.matchName = matchInfo.name
                    resultRound.round = row.select("div.player-result-round").first().text()
                    resultRound.round = translateRound(resultRound.round)
                    //player1
                    val name1 = row.select("div.player-result-name-1 div.name")
                    if (name1.size > 0) {
                        resultRound.player1 = translatePlayerName(name1[0].select("a").first().text())
                    }
                    if (name1.size > 1) {
                        resultRound.player12 = translatePlayerName(name1[1].select("a").first().text())
                    }
                    val flag1 = row.select("div.player-result-flag-1 div.flag")
                    if (flag1.size > 0) {
                        resultRound.flag1 = flag1[0].select("img").first().attr("src")
                    }
                    if (flag1.size > 1) {
                        resultRound.flag12 = flag1[1].select("img").first().attr("src")
                    }
                    //player1
                    val name2 = row.select("div.player-result-name-2 div.name")
                    if (name2.size > 0) {
                        resultRound.player2 = translatePlayerName(name2[0].select("a").first().text())
                    }
                    if (name2.size > 1) {
                        resultRound.player22 = translatePlayerName(name2[1].select("a").first().text())
                    }
                    val flag2 = row.select("div.player-result-flag-2 div.flag")
                    if (flag2.size > 0) {
                        val img=flag2[0].select("img")
                        if(img.size>0){
                            resultRound.flag2 = img.first().attr("src")
                        }


                    }
                    if (flag2.size > 1) {
                        val img=flag2[1].select("img")
                        if(img.size>0){
                            resultRound.flag22 = img.first().attr("src")
                        }

                    }

                    resultRound.vs = row.select("div.player-result-vs").first().text()
                    resultRound.score = row.select("div.player-result-win span").first().text()
                    resultRound.duration = "时长：" + row.select("div.player-result-duration div.timer").first().text()
                    roundList.add(resultRound)
                }
                matchBean.rounds = roundList
                data.add(matchBean)
            }

        }
        return data
    }

    private fun translateRound(round: String?): String? {
        when (round) {
            "Final" -> return "决赛"
            "SF" -> return "半决赛"
            "QF" -> return "1/4决赛"
            "R16" -> return "1/8决赛"
            "R32" -> return "1/16决赛"
            "R3"->return "第三轮"
            "R2"->return "第二轮"
            "R1"->"第一轮"
        }
        return round
    }

    private fun translateMatchName(key: String): String {

        val matchNameNotYear = key.replace("\\d+".toRegex(), "").trim { it <= ' ' }
        val value = matchNameMap[matchNameNotYear.toUpperCase()]
        return if (TextUtils.isEmpty(value)) {
            key
        } else {
            key.replace(matchNameNotYear, value!!)
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
}
