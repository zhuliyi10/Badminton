package com.leory.badminton.news.mvp.presenter

import android.text.TextUtils
import com.leory.badminton.news.mvp.contract.MatchDetailContract
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
import java.util.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Describe : 赛事详情presenter
 * Author : zhuly
 * Date : 2019-05-27
 */

@FragmentScope
class MatchAgainstPresenter @Inject constructor(@Named("player_name")
                                                var playerNameMap: HashMap<String, String>? = null,
                                                @Named("detail_url")
                                                var detailUrl: String? = null,
                                                @Named("match_classify")
                                                var matchClassify: String? = null,
                                                model: MatchDetailContract.Model,
                                                rootView: MatchDetailContract.MatchAgainView) :
        BasePresenter<MatchDetailContract.Model, MatchDetailContract.MatchAgainView>(model, rootView) {

    private var againstData: List<List<AgainstFlowBean>>? = null
    private var currentMatchSchedule: String? = null


    /**
     * 是否是团体赛
     *
     * @return
     */
    val isGroup: Boolean
        get() = "Grade 1 - Team Tournaments" == matchClassify

    fun requestData(type: String?) {

        if (detailUrl != null) {
            val requestUrl: String

            if (isGroup) {
                requestUrl = detailUrl!! + "draw/group-1"
            } else {
                var enType = "ms"
                when (type) {
                    "男单" -> enType = "ms"
                    "女单" -> enType = "ws"
                    "男双" -> enType = "md"
                    "女双" -> enType = "wd"
                    "混双" -> enType = "xd"
                }

                if ("第一级别 个人赛" == matchClassify) {
                    requestUrl = detailUrl + "draw/" + enType
                } else {
                    requestUrl = detailUrl + "result/draw/" + enType
                }
            }

            model.getMatchDetail(requestUrl)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { rootView.showLoading() }.subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally { rootView.hideLoading() }
                    .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                    .subscribe(object : RxHandlerSubscriber<String>() {

                        override fun onNext(o: String) {
                            parseHtmlResult(o, type)
                        }

                    })
        }
    }

    /**
     * 选择比赛进度
     *
     * @param scheduleText
     */
    fun selectSchedule(scheduleText: String, pos: Int) {
        this.currentMatchSchedule = scheduleText
        showAgainView(pos)
    }

    private fun showAgainView(level: Int) {
        if (againstData != null) {
            val selectData = ArrayList<List<AgainstFlowBean>>()
            for (i in 0..1) {
                if (i + level < againstData?.size ?: 0) {
                    selectData.add(againstData!![i + level])
                }
            }
            if (level + 2 < againstData?.size ?: 0)
                for (i in againstData!![level + 2].indices) {
                    selectData.add(ArrayList())
                }
            if (rootView != null) {
                rootView.showAgainstView(selectData)
            }
        }

    }


    /**
     * 解析html
     *
     * @param html
     */
    private fun parseHtmlResult(html: String, type: String?) {
        Observable.just(html)

                .flatMap { Observable.just(getAgainstData(html, type)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(object : Observer<List<List<AgainstFlowBean>>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(data: List<List<AgainstFlowBean>>) {
                        againstData = data
                        if (rootView != null) {
                            rootView.showMatchSchedule(getMatchSchedules(data.size - 1))
                        }

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
    }


    private fun getAgainstData(html: String?, type: String?): List<List<AgainstFlowBean>> {
        val isDouble = isDouble(type)
        val data = ArrayList<ArrayList<AgainstFlowBean>>()
        if (html != null) {
            val doc = Jsoup.parse(html)
            val trs = doc.select("tr")
            if (trs.size > 2) {
                val colNum = (Math.log((trs.size - 2).toDouble()) / Math.log(2.0)).toInt()
                for (i in 0 until colNum) {
                    data.add(ArrayList())
                }
                for (i in 2 until trs.size) {
                    if (colNum > 0) {
                        if (i % 2 == 0) {
                            val bean = AgainstFlowBean()
                            val td = trs[i].select("td")[1]
                            setItemData(bean, isDouble, td)
                            data[0].add(bean)

                        }
                    }
                    if (colNum > 1) {
                        if (i % 4 == 3) {
                            val bean = AgainstFlowBean()
                            val td = trs[i].select("td#col-2").first()
                            setItemData(bean, isDouble, td)
                            data[1].add(bean)
                        }
                        if (i % 4 == 0) {//比分
                            val element = trs[i].select("td div.draw-score").first()
                            if (element != null) {
                                data[1][data[1].size - 1].score = translateScore(element.text())
                            }
                        }
                    }
                    if (colNum > 2) {
                        if (i % 8 == 5) {
                            val bean = AgainstFlowBean()
                            val td = trs[i].select("td#col-3").first()
                            setItemData(bean, isDouble, td)
                            data[2].add(bean)
                        }
                        if (i % 8 == 6) {
                            val element = trs[i].select("td div.draw-score").first()
                            if (element != null) {
                                data[2][data[2].size - 1].score = translateScore(element.text())
                            }
                        }
                    }
                    if (colNum > 3) {
                        if (i % 16 == 9) {
                            val bean = AgainstFlowBean()
                            val td = trs[i].select("td#col-4").first()
                            setItemData(bean, isDouble, td)
                            data[3].add(bean)
                        }
                        if (i % 16 == 10) {
                            val element = trs[i].select("td div.draw-score").first()
                            if (element != null) {
                                data[3][data[3].size - 1].score = translateScore(element.text())
                            }
                        }
                    }
                    if (colNum > 4) {
                        if (i % 32 == 17) {
                            val bean = AgainstFlowBean()
                            val td = trs[i].select("td#col-5").first()
                            setItemData(bean, isDouble, td)
                            data[4].add(bean)
                        }
                        if (i % 32 == 18) {
                            val element = trs[i].select("td div.draw-score").first()
                            if (element != null) {
                                data[4][data[4].size - 1].score = translateScore(element.text())
                            }
                        }
                    }
                    if (colNum > 5) {
                        if (i % 64 == 33) {
                            val bean = AgainstFlowBean()
                            val td = trs[i].select("td#col-6").first()
                            setItemData(bean, isDouble, td)
                            data[5].add(bean)
                        }
                        if (i % 64 == 34) {
                            val element = trs[i].select("td div.draw-score").first()
                            if (element != null) {
                                data[5][data[5].size - 1].score = translateScore(element.text())
                            }
                        }
                    }
                    if (colNum > 6) {
                        if (i % 128 == 65) {
                            val bean = AgainstFlowBean()
                            val td = trs[i].select("td#col-6").first()
                            setItemData(bean, isDouble, td)
                            data[6].add(bean)
                        }
                        if (i % 128 == 66) {
                            val element = trs[i].select("td div.draw-score").first()
                            if (element != null) {
                                data[6][data[6].size - 1].score = translateScore(element.text())
                            }
                        }
                    }
                }

            }
        }


        return data
    }

    private fun translateScore(score: String): String {
        return score.replace("Walkover", "退赛").replace("Retired", "退赛")
    }


    private fun setItemData(bean: AgainstFlowBean, isDouble: Boolean, td: Element) {
        bean.isDouble = isDouble
        var element: Element?
        if (isDouble) {
            element = td.select("div.draw-player1-wrap img").first()
            if (element != null) {
                bean.icon1 = addHttps(element.attr("src"))
            }
            element = td.select("div.draw-player1-wrap a").first()
            if (element != null) {
                bean.name1 = translatePlayerName(element.text())
            }
            element = td.select("div.draw-player2-wrap img").first()
            if (element != null) {
                bean.icon2 = addHttps(element.attr("src"))
            }
            element = td.select("div.draw-player2-wrap a").first()
            if (element != null) {
                bean.name2 = translatePlayerName(element.text())
            }
        } else {
            element = td.select("div.draw-player1-wrap img").first()
            if (element != null) {
                bean.icon1 = addHttps(element.attr("src"))
            }
            element = td.select("div.draw-player1-wrap a").first()
            if (element == null) {
                element = td.select("div.draw-player1-wrap div.draw-name").first()
            }
            if (element != null) {
                if (!element.text().contains("Match Time")) {
                    bean.name1 = translatePlayerName(element.text())
                }
            }
        }
    }

    /**
     * 是否是双打
     *
     * @param type
     * @return
     */
    private fun isDouble(type: String?): Boolean {
        return "男双" == type || "女双" == type || "混双" == type
    }

    private fun addHttps(img: String?): String? {
        return if (img != null) {
            if (!img.startsWith("https:")) {
                "https:$img"
            } else {
                img
            }
        } else null
    }


    /**
     * 获取标签数据
     *
     * @param count
     * @return
     */
    private fun getMatchSchedules(count: Int): List<String> {
        val data = ArrayList<String>()
        val schedules = arrayOf("1/32决赛", "1/16决赛", "1/8决赛", "1/4决赛", "半决赛", "决赛")
        var i = schedules.size - count
        while (i >= 0 && i < schedules.size) {
            data.add(schedules[i])
            i++
        }
        return data
    }

    private fun translatePlayerName(key: String): String? {
        if (key.contains("Qualification") || key.contains("Bye")) {
            return null
        }
        val playerName = key.replace("\\[\\d+\\]".toRegex(), "").trim { it <= ' ' }
        val value = playerNameMap!![playerName]
        return if (TextUtils.isEmpty(value)) {
            key
        } else {
            key.replace(playerName, value!!)
        }
    }
}
