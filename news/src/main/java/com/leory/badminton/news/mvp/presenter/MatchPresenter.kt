package com.leory.badminton.news.mvp.presenter

import android.graphics.Color
import android.text.TextUtils

import com.leory.badminton.news.app.utils.FileHashUtils
import com.leory.badminton.news.mvp.contract.MatchContract
import com.leory.badminton.news.mvp.model.bean.MatchItemBean
import com.leory.badminton.news.mvp.model.bean.MatchItemSection
import com.leory.commonlib.di.scope.FragmentScope
import com.leory.commonlib.http.RxHandlerSubscriber
import com.leory.commonlib.mvp.BasePresenter
import com.leory.commonlib.utils.LogUtils
import com.leory.commonlib.utils.RxLifecycleUtils

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import java.util.ArrayList
import java.util.HashMap

import javax.inject.Inject

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Describe :赛事presenter
 * Author : leory
 * Date : 2019-05-19
 */
@FragmentScope
class MatchPresenter @Inject constructor(model: MatchContract.Model, rootView: MatchContract.View) :
        BasePresenter<MatchContract.Model, MatchContract.View>(model, rootView) {

    private val matchCategoryMap: HashMap<String, String> by lazy {
        FileHashUtils.getMatchCategory() as HashMap<String, String>
    }
    private val matchNameMap: HashMap<String, String> by lazy {
        FileHashUtils.getMatchName() as HashMap<String, String>
    }

    fun requestData(year: String, finish: String) {
        val state: String
        if ("全部" == finish) {
            state = "all"
        } else if ("已完成" == finish) {
            state = "completed"
        } else {
            state = "remaining"
        }
        model.getMatchList(year, state)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { disposable -> rootView.showLoading() }.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(object : RxHandlerSubscriber<String>() {

                    override fun onNext(o: String) {
                        parseHtmlResult(o)
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        rootView.hideLoading()
                    }
                })
    }

    /**
     * 解析html
     *
     * @param html
     */
    private fun parseHtmlResult(html: String) {
        Observable.just(html)

                .flatMap{ Observable.just(getMatchData(html)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { rootView.hideLoading() }
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(object : Observer<List<MatchItemSection>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(matchItemSections: List<MatchItemSection>) {
                        rootView.showMatchData(matchItemSections)
                    }

                    override fun onError(e: Throwable) {
                        rootView.showMatchData(ArrayList())
                    }

                    override fun onComplete() {

                    }
                })
    }


    private fun getMatchData(html: String?): List<MatchItemSection> {
        val matchList = ArrayList<MatchItemSection>()
        if (html != null) {
            val doc = Jsoup.parse(html)
            val months = doc.select("div.item-results")

            for (month in months) {
                val monthName = month.select("h2").first().text()
                LogUtils.d(TAG, monthName)
                matchList.add(MatchItemSection(true, monthName))
                val list = month.select("tr[class=black],tr[class=gray1],tr[class=gray2],tr[class=gray3]")
                var i = 0
                for (item in list) {

                    val tr = item.select("td")
                    if (tr.size == 7) {

                        val bean = MatchItemBean()
                        val matchClassify = tr[5].select("div[class=name]").first().text()//赛事类别
                        if ("HSBC BWF World Tour Super 300" == matchClassify ||
                                "HSBC BWF World Tour Super 500" == matchClassify ||
                                "HSBC BWF World Tour Super 750" == matchClassify ||
                                "HSBC BWF World Tour Super 1000" == matchClassify ||
                                "HSBC BWF World Tour Finals" == matchClassify ||
                                "Grade 1 - Individual Tournaments" == matchClassify ||
                                "Grade 1 - Team Tournaments" == matchClassify) {
                            bean.matchClassify = translateMatchCategory(matchClassify)
                        } else {
                            continue
                        }
                        val countryName = tr[1].select("div[class=country_code]").first().text()//国家简名
                        bean.countryName = countryName
                        val countryFlagUrl = tr[1].select("img").first().attr("src")//国旗url
                        bean.countryFlagUrl = countryFlagUrl
                        val cityName = tr[6].select("div").first().text()//城市名字
                        bean.cityName = cityName
                        val matchDay = tr[2].text()//比赛日期
                        bean.matchDay = monthName + "\n" + matchDay
                        val matchName = tr[3].select("a").first().text()//赛事名称
                        bean.matchName = translateMatchName(matchName)
                        bean.matchUrl = tr[3].select("a").first().attr("href")

                        val matchBonus = tr[4].select("div").first().text()//赛事奖金
                        bean.matchBonus = matchBonus
                        if (i % 2 == 0) {
                            bean.bgColor = Color.WHITE
                        } else {
                            bean.bgColor = Color.parseColor("#f5f5f5")
                        }
                        matchList.add(MatchItemSection(bean))

                        i++
                    }
                }
            }

        }
        return matchList
    }

    private fun translateMatchName(key: String): String? {

        val matchNameNotYear = key.replace("\\d+".toRegex(), "").trim { it <= ' ' }
        val value = matchNameMap[matchNameNotYear.toUpperCase()]
        return if (TextUtils.isEmpty(value)) {
            key
        } else {
            key.replace(matchNameNotYear, value!!)
        }
    }

    private fun translateMatchCategory(key: String): String? {

        val value = matchCategoryMap[key]
        return if (TextUtils.isEmpty(value)) {
            key
        } else {
            value
        }
    }
}
