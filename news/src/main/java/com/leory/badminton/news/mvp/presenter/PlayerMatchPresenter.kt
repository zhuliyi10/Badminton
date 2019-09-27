package com.leory.badminton.news.mvp.presenter

import android.text.TextUtils
import com.leory.badminton.news.app.utils.FileHashUtils
import com.leory.badminton.news.mvp.contract.PlayerContract
import com.leory.badminton.news.mvp.model.bean.PlayerMatchBean
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
 * Describe : 运动员赛果
 * Author : leory
 * Date : 2019-06-11
 */
@FragmentScope
class PlayerMatchPresenter @Inject constructor(model: PlayerContract.Model, rootView: PlayerContract.MatchView, @param:Named("player_url") private val playerUrl: String?) :
        BasePresenter<PlayerContract.Model, PlayerContract.MatchView>(model, rootView) {

    private val matchNameMap: HashMap<String, String> by lazy {
        FileHashUtils.matchName as HashMap<String, String>
    }
    private val playerNameMap: HashMap<String, String> by lazy {
        FileHashUtils.playerName as HashMap<String, String>
    }

    fun requestData(year: String?) {
        if (playerUrl != null) {
            val requestUrl = "$playerUrl/tournament-results/"
            model.getPlayerMatches(requestUrl, year)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { disposable -> rootView.showLoading() }.subscribeOn(AndroidSchedulers.mainThread())
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
                .flatMap { Observable.just(getPlayerMatchData(html)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(object : Observer<List<PlayerMatchBean>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(data: List<PlayerMatchBean>) {
                        rootView.showMatchData(data)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
    }

    private fun getPlayerMatchData(html: String?): List<PlayerMatchBean> {
        val data = ArrayList<PlayerMatchBean>()
        if (html != null) {
            val doc = Jsoup.parse(html)
            val matches = doc.select("div.box-profile-tournament")
            val roundHeads = doc.select("div.title-tournament-matches")
            val rounds = doc.select("div.tournament-matches")
            if (matches.size != rounds.size && roundHeads.size == rounds.size) {
                var i = 0
                while (i < roundHeads.size) {
                    val title = roundHeads[i].select("a").first().text()
                    if (title.contains("循环配置")) {
                        roundHeads.removeAt(i)
                        rounds.removeAt(i)
                        continue
                    }
                    i++
                }
            }
            if (matches.size == rounds.size) {
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
                    val rows = round.select("div.col-1-2 div.tournament-matches-row")
                    for (row in rows) {
                        val resultRound = PlayerMatchBean.ResultRound()
                        resultRound.round = row.select("div.player-result-round").first().text()
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
                            resultRound.flag2 = flag2[0].select("img").first().attr("src")
                        }
                        if (flag2.size > 1) {
                            resultRound.flag22 = flag2[1].select("img").first().attr("src")
                        }

                        resultRound.vs = row.select("div.player-result-vs").first().text()
                        resultRound.score = row.select("div.player-result-win span").first().text()
                        resultRound.duration = "时长：" + round.select("div.player-result-duration div.timer").first().text()
                        roundList.add(resultRound)
                    }
                    matchBean.rounds = roundList
                    data.add(matchBean)
                }
            }
        }
        return data
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
