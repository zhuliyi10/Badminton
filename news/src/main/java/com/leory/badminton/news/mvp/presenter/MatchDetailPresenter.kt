package com.leory.badminton.news.mvp.presenter

import android.text.TextUtils
import com.leory.badminton.news.app.utils.FileHashUtils
import com.leory.badminton.news.mvp.contract.MatchDetailContract
import com.leory.badminton.news.mvp.model.bean.MatchInfoBean
import com.leory.badminton.news.mvp.model.bean.MatchTabDateBean
import com.leory.commonlib.di.scope.ActivityScope
import com.leory.commonlib.http.RxHandlerSubscriber
import com.leory.commonlib.mvp.BasePresenter
import com.leory.commonlib.utils.LogUtils
import com.leory.commonlib.utils.RxLifecycleUtils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Named

/**
 * Describe : 赛事详情presenter
 * Author : zhuly
 * Date : 2019-05-27
 */
@ActivityScope
class MatchDetailPresenter @Inject
constructor(model: MatchDetailContract.Model, rootView: MatchDetailContract.View, @param:Named("detail_url") internal var detailUrl: String?, @param:Named("match_classify") internal var matchClassify: String) : BasePresenter<MatchDetailContract.Model, MatchDetailContract.View>(model, rootView) {
    private val matchNameMap: HashMap<String, String> by lazy {
        FileHashUtils.matchName
    }
    private val monthMap: HashMap<String, String> by lazy {
        FileHashUtils.month
    }
    /**
     * 是否是团体赛
     *
     * @return
     */
    private val isGroup: Boolean
        get() = "Grade 1 - Team Tournaments" == matchClassify

    init {
        requestMatchInfo()
    }

    private fun requestMatchInfo() {
        detailUrl?.apply {
            var requestUrl = this + "results/podium/"
            if (this.contains("bwfworldtourfinals")){//年终总决赛
                requestUrl=this+"results/"
            }else if (isGroup) {//团体赛
                requestUrl = this + "podium"
            }
            model.getMatchInfo(requestUrl)
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                    .subscribe(object : RxHandlerSubscriber<String>() {

                        override fun onNext(o: String) {
                            parseHtmlMatchInfo(o)
                        }

                    })
        }
    }


    private fun parseHtmlMatchInfo(html: String) {
        Observable.just(html)

                .flatMap { Observable.just(getMatchInfo(html)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(object : Observer<MatchInfoBean> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(bean: MatchInfoBean) {
                        rootView.showMatchInfo(bean)
                    }

                    override fun onError(e: Throwable) {
                        LogUtils.d(TAG, e.message)
                    }

                    override fun onComplete() {

                    }
                })
    }

    private fun getMatchInfo(html: String?): MatchInfoBean {
        val bean = MatchInfoBean()
        if (html != null) {

            val doc = Jsoup.parse(html)
            val p = Pattern.compile("document.getElementById(.*).style.backgroundImage(.*).jpg")
            var m = p.matcher(html)
            val isFind = m.find()
            if (isFind) {
                val group = m.group()
                m = Pattern.compile("https(.*).jpg").matcher(group)
                if (m.find()) {
                    val bgUrl = m.group()
                    LogUtils.d(TAG, bgUrl)
                    bean.matchBackground = bgUrl
                }

            }
            val head = doc.select("div.box-results-tournament").first()
            var element: Element? = head.select("div.info h2").first()
            if (element != null) {
                bean.matchName = translateMatchName(element.text())
            }
            element = head.select("div.info h4").first()
            if (element != null) {
                bean.matchDate = translateMonth(element.text())
            }
            element = head.select("div.info h5").first()
            if (element != null) {
                bean.matchSite = element.text()
                val strings = element.text().split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (strings.size > 0) {
                    bean.country = strings[strings.size - 1].trim { it <= ' ' }
                }
            } else {
                element = head.select("div.info h4").first()
                val temp = element!!.text().split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (temp.size > 1) {
                    bean.country = temp[1].trim { it <= ' ' }
                }
            }
            element = head.select("div.info div.prize").first()
            if (element != null) {
                bean.matchBonus = element.text().replace("PRIZE MONEY USD", "奖金:$")
            }
            element = head.select("div.logo-right img").first()
            if (element != null) {
                bean.matchIcon = element.attr("src")
            }

            var ul: Element? = doc.select("ul#ajaxTabsResults").first()
            if (ul == null) {
                ul = doc.select("ul#ajaxTabs").first()
            }
            if (ul != null) {
                val lis = ul.select("li")
                if (lis != null) {
                    val headTabs = ArrayList<MatchTabDateBean>()
                    for (i in 1 until lis.size - 1) {
                        val li = lis[i]
                        val link = li.select("a").first().attr("href")
                        val name = translateMonth(li.select("a").first().text())
                        headTabs.add(MatchTabDateBean(link, name))
                    }
                    bean.tabDateHeads = headTabs

                }
            }

            var li: Element? = doc.select("ul#ajaxTabsTmt li").first()
            if (li == null) {
                li = doc.select("ul#ajaxTabs li").first()
            }
            if (li != null) {
                bean.historyUrl = li.select("a").first().attr("href")
            }

        }
        return bean
    }

    private fun translateMatchName(key: String): String {
        val matchNameNotYear = key.replace("\\d+".toRegex(), "").trim { it <= ' ' }
        val value = matchNameMap!![matchNameNotYear.toUpperCase()]
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
}
