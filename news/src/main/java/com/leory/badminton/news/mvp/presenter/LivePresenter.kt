package com.leory.badminton.news.mvp.presenter

import android.text.TextUtils
import com.leory.badminton.news.app.utils.FileHashUtils
import com.leory.badminton.news.mvp.contract.LiveContract
import com.leory.badminton.news.mvp.model.bean.LiveBean
import com.leory.badminton.news.mvp.model.bean.LiveDetailBean
import com.leory.commonlib.di.scope.FragmentScope
import com.leory.commonlib.http.RxHandlerSubscriber
import com.leory.commonlib.mvp.BasePresenter
import com.leory.commonlib.utils.LogUtils
import com.leory.commonlib.utils.RxLifecycleUtils
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * Describe : 直播presenter
 * Author : leory
 * Date : 2019-06-03
 */
@FragmentScope
class LivePresenter @Inject constructor(model: LiveContract.Model, rootView: LiveContract.View) :
        BasePresenter<LiveContract.Model, LiveContract.View>(model, rootView) {

    private val matchNameMap: HashMap<String, String> by lazy {
        FileHashUtils.matchName
    }
    private val monthMap: HashMap<String, String> by lazy {
        FileHashUtils.month
    }
    private val playerNameMap: HashMap<String, String> by lazy {
        FileHashUtils.playerName
    }

    fun requestData() {
        model.liveMatch
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { rootView.showLoading() }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { rootView.hideLoading() }
                .compose(RxLifecycleUtils.bindUntilEvent(rootView,FragmentEvent.DESTROY))
                .subscribe(object : RxHandlerSubscriber<String>() {
                    override fun onNext(s: String) {
                        parseHtmlData(s)
                    }
                })
    }


    private fun parseHtmlData(html: String) {
        Observable.just(html)

                .flatMap { Observable.just(getLiveData(html)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(rootView,FragmentEvent.DESTROY))
                .subscribe(object : Observer<LiveBean> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(bean: LiveBean) {
                        rootView.showLiveData(bean)
                    }

                    override fun onError(e: Throwable) {
                        LogUtils.d("error")
                    }

                    override fun onComplete() {

                    }
                })

    }

    private fun getLiveData(html: String?): LiveBean {
        val bean = LiveBean()
        if (html != null) {
            val doc = Jsoup.parse(html)

            var detailUrl = doc.select("div.live-scores-box-single a").first().attr("href")
            if (detailUrl.endsWith("live/")) {
                requestLiveUrl(detailUrl)

                detailUrl = detailUrl.replace("live/", "")

            } else {
                detailUrl = detailUrl.replace("results/", "")
            }
            bean.detailUrl = detailUrl
            val data = doc.select("div.live-scores-box-wrap").first()
            val matchName = data.select("h2").first().text()
            bean.matchName = translateMatchName(matchName)
            val matchDate = data.select("h3").first().text()
            bean.matchDate = translateMonth(matchDate)
            val flag = data.select("div.flag img").first().attr("src")
            bean.countryFlag = flag
            val spans = data.select("div.country span")
            if (spans.size == 2) {
                bean.country = spans[1].text()
                bean.city = spans[0].text()
            }
            bean.matchIcon = data.select("div.cat-logo img").first().attr("src")
        }
        return bean

    }


    private fun requestLiveUrl(detailUrl: String) {
        model.getLiveUrl(detailUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(object : RxHandlerSubscriber<String>() {
                    override fun onNext(s: String) {
                        val liveUrl = getLiveUrl(s)
                        if (liveUrl != null) {
                            requestLive(liveUrl)
                        }
                    }
                })
    }

    private fun getLiveUrl(html: String): String? {
        var m = Pattern.compile("document.location='https://bwfworldtour.bwfbadminton.com/(.*)?match=(.*)&tab=live';").matcher(html)
        if (m.find()) {
            val group = m.group()
            m = Pattern.compile("https(.*)tab=live").matcher(group)
            if (m.find()) {
                val liveUrl = m.group()
                LogUtils.d(TAG, liveUrl)
                return liveUrl
            }
        }
        return null
    }

    /**
     * 请求直播中数据
     *
     * @param liveUrl
     */
    private fun requestLive(liveUrl: String?) {
        Observable.interval(0, 5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap {
                    model.getLiveDetail(liveUrl)
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(rootView,FragmentEvent.DESTROY))
                .subscribe(object : RxHandlerSubscriber<String>(compositeDisposable) {
                    override fun onNext(s: String) {
                        parseLiveDetail(s)
                    }
                })
    }

    private fun parseLiveDetail(html: String) {
        Observable.just(html)

                .flatMap { Observable.just(getLiveDetails(html)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(rootView,FragmentEvent.DESTROY))
                .subscribe(object : Observer<List<LiveDetailBean>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(list: List<LiveDetailBean>) {
                        rootView.showLiveDetail(list)
                    }

                    override fun onError(e: Throwable) {}

                    override fun onComplete() {

                    }
                })
    }

    private fun getLiveDetails(html: String?): List<LiveDetailBean> {
        val data = ArrayList<LiveDetailBean>()
        if (html != null) {
            val doc = Jsoup.parse(html)
            val lis = doc.select("li")
            if (lis != null) {
                for (li in lis) {
                    val bean = LiveDetailBean()
                    bean.detailUrl = li.select("a").first().attr("href")
                    bean.type = translateType(li.select("div.round strong").first().text())
                    bean.field = li.select("div.court").first().text().replace("Court", "场地")
                    val element = li.select("div.time").first()
                    if (element != null) {
                        bean.time = element.text()
                    }


                    //player1
                    val player1s = li.select("div.player1 span")
                    bean.player1 = translatePlayerName(player1s.first().text())
                    if (player1s.size == 2) {
                        bean.player12 = translatePlayerName(player1s[1].text())
                    }

                    //flag1
                    val flags = li.select("div.flag1 img")

                    var flag1: String? = flags.first().attr("src")
                    if (flag1 != null && !flag1.startsWith("https:")) {
                        flag1 = "https:$flag1"
                    }
                    bean.flag1 = flag1
                    if (flags.size == 2) {
                        flag1 = flags[1].attr("src")
                        if (flag1 != null && !flag1.startsWith("https:")) {
                            flag1 = "https:$flag1"
                        }
                        bean.flag12 = flag1
                    }

                    //player2
                    val player2s = li.select("div.player2 span")
                    bean.player2 = translatePlayerName(player2s.first().text())
                    if (player2s.size == 2) {
                        bean.player22 = translatePlayerName(player2s[1].text())
                    }

                    //flag2
                    val flag2s = li.select("div.flag2 img")
                    var flag2: String? = flag2s.first().attr("src")
                    if (flag2 != null && !flag2.startsWith("https:")) {
                        flag2 = "https:$flag2"
                    }
                    bean.flag2 = flag2
                    if (flag2s.size == 2) {
                        flag2 = flag2s[1].attr("src")
                        if (flag2 != null && !flag2.startsWith("https:")) {
                            flag2 = "https:$flag2"
                        }
                        bean.flag22 = flag2
                    }

                    bean.vs = li.select("div.vs").first().text()
                    bean.leftDot = li.select("div.score-serve-1").first().text()
                    bean.rightDot = li.select("div.score-serve-2").first().text()
                    bean.score = li.select("div.score").first().text()
                    data.add(bean)
                }
            }
        }
        return data
    }


    private fun translateType(type: String): String = when (type) {
        "MS" -> "男单"
        "WS" -> "女单"
        "MD" -> "男双"
        "WD" -> "女双"
        "XD" -> "混双"
        else -> type
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

    private fun translateMonth(key: String?): String? {
        if (key == null) return null
        val month = key.replace("\\d+".toRegex(), "").replace("-".toRegex(), "").trim { it <= ' ' }
        val value = monthMap[month]
        return if (TextUtils.isEmpty(value)) {
            key
        } else {
            key.replace(month, value!!)
        }
    }

    private fun translatePlayerName(key: String): String? {
        val playerName = key.replace("\\[\\d+\\]".toRegex(), "").trim { it <= ' ' }
        val value = playerNameMap[playerName]
        return if (TextUtils.isEmpty(value)) {
            key
        } else {
            key.replace(playerName, value!!)
        }
    }
}
