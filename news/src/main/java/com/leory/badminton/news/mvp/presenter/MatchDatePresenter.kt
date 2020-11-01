package com.leory.badminton.news.mvp.presenter

import android.text.TextUtils
import android.util.Log
import com.leory.badminton.news.app.utils.FileHashUtils
import com.leory.badminton.news.mvp.contract.MatchDetailContract
import com.leory.badminton.news.mvp.model.MatchDetailModel
import com.leory.badminton.news.mvp.model.bean.MatchDateBean
import com.leory.badminton.news.mvp.model.bean.MatchTabDateBean
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
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Named

/**
 * Describe : 比赛赛程presenter
 * Author : leory
 * Date : 2019-06-06
 */
@FragmentScope
class MatchDatePresenter @Inject constructor(@Named("player_name")
                                             val playerNameMap: HashMap<String, String>,
                                             private val dateBeans: MutableList<MatchTabDateBean>? = null,
                                             @Named("country")
                                             val country: String,
                                             model: MatchDetailModel,
                                             rootView: MatchDetailContract.MatchDateView) :
        BasePresenter<MatchDetailModel, MatchDetailContract.MatchDateView>(model, rootView) {

    private val timeDifferMap: HashMap<String, String> by lazy {
        FileHashUtils.timeDiffer as HashMap<String, String>
    }
    private var currentData: List<MatchDateBean>? = null

    private val timeDiffer: Int
        get() {

            val value = timeDifferMap[country]
            return if (!TextUtils.isEmpty(value)) {
                Integer.parseInt(value!!)
            } else 0
        }

    fun filter(filter: String, state: String) {
        if (currentData != null) {
            val tempData = ArrayList<MatchDateBean>()
            if (filter == "国羽") {
                for (bean in currentData!!) {
                    if (bean.flag1?.contains("china") == true
                            || bean.flag2?.contains("china") == true
                            || bean.flag12?.contains("china") == true
                            || bean.flag22?.contains("china") == true) {
                        tempData.add(bean)
                    }
                }
            } else {
                for (bean in currentData!!) {
                    tempData.add(bean)
                }
            }
            rootView.showDateData(sort(state, tempData))
        }
    }

    private fun sort(state: String, data: List<MatchDateBean>): List<MatchDateBean> {

        if (state == "时间") {
            val all = ArrayList<MatchDateBean>()
            val finishList = ArrayList<MatchDateBean>()
            val unFinishList = ArrayList<MatchDateBean>()
            for (bean in data) {
                if (bean.duration?.contains("时长") == true) {
                    finishList.add(bean)
                } else {
                    unFinishList.add(bean)
                }
            }
            all.addAll(finishList)
            all.addAll(unFinishList)
            return all
        } else {
            return data
        }

    }

    private var nextMin=60
    fun requestPosition(pos: Int, match: String?) {
        nextMin = if(pos==0){
            45
        }else{
            60
        }
        model.getMatchDate(dateBeans?.let { it[pos].link } ?: "", match ?: "")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { rootView.showLoading() }.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { rootView.hideLoading() }
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(object : RxHandlerSubscriber<String>() {

                    override fun onNext(o: String) {
                        parseHtmlResult(o, match)
                    }

                })

    }

    private fun parseHtmlResult(html: String, match: String?) {
        Observable.just(html)

                .flatMap { Observable.just(getMatchDateData(html)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(object : Observer<List<MatchDateBean>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(data: List<MatchDateBean>) {
                        if (match != null && data != null) {
                            for (bean in data) {
                                if (match == bean.matchId) {
                                    rootView.toHistoryDetail(getHandOffUrl(bean))
                                }
                            }
                        }
                        if (match == null) {
                            currentData = data
                            rootView.showDateData(data)
                        }
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "onError: ")
                    }

                    override fun onComplete() {

                    }
                })
    }


    private fun getMatchDateData(html: String?): List<MatchDateBean> {
        val data = ArrayList<MatchDateBean>()
        if (html != null) {
            val doc = Jsoup.parse(html)
            val lis = doc.select("li.draw-MS,li.draw-WS,li.draw-MD,li.draw-WD,li.draw-XD,li.stats")
            if (lis != null) {
                var i = 1
                while (i < lis.size) {
                    val li = lis[i]
                    val bean = MatchDateBean()
                    val matchId = getParam(li.select("a#match-link").attr("href"), "match")
                    bean.matchId = matchId
                    bean.type = translateType(li.select("div.round").first().text().replace(" - Qualification", ""))
                    val court = li.select("span.round-court").first().text()
                    if (TextUtils.isEmpty(court)) {
                        bean.field = "流水场"
                    } else if (TextUtils.isDigitsOnly(court)) {
                        bean.field = "场地 $court"
                    } else {
                        bean.field=court.replace("Minoru Yoneyama","场地1").replace("M Yoneyama Court","场地1")
                                .replace("Quaycentre", "场地").replace("court", "场地").replace("Court", "场地")

                    }

                    bean.field?.apply {
                        bean.field=replace(" ","")
                    }


                    bean.time = getChineseTime(translateTime(li.select("div.time").first().text()))
                    bean.time?.apply {
                        val strs=split(".")
                        if(strs.size>1){
                            bean.time=strs[1].trim()
                        }
                    }


                    //player1
                    val player11 = li.select("div.player1-wrap").first()
                    if (player11 != null) {
                        bean.player1 = translatePlayerName(player11.select("div.player1").first().text())
                        bean.flag1 = player11.select("div.flag img").attr("src")
                        val player11Detail = lis[i - 1]
                        val player11Url = player11Detail.select("div.player1-name a").first()
                        if (player11Url != null) {
                            bean.player1Url = player11Url.attr("href")
                        }

                    }
                    //player12
                    val player12 = li.select("div.player2-wrap").first()
                    if (player12 != null) {
                        bean.player12 = translatePlayerName(player12.select("div.player2").first().text())
                        bean.flag12 = player12.select("div.flag img").attr("src")
                        val player12Detail = lis[i - 1]
                        val player12Url = player12Detail.select("div.player1-name a")
                        if (player12Url != null && player12Url.size > 1) {
                            bean.player12Url = player12Url[1].attr("href")
                        }
                    }
                    //player2
                    val player21 = li.select("div.player3-wrap").first()

                    if (player21 != null) {
                        val player21Name = player21.select("div.player3").first()
                        if (player21Name != null) {
                            bean.player2 = translatePlayerName(player21Name.text())
                            bean.flag2 = player21.select("div.flag img").attr("src")
                            val player21Detail = lis[i - 1]
                            val player21Url = player21Detail.select("div.player2-name a").first()
                            if (player21Url != null) {
                                bean.player2Url = player21Url.attr("href")
                            }

                        }
                    }
                    //player22
                    val player22 = li.select("div.player4-wrap").first()

                    if (player22 != null) {
                        val player22Name = player22.select("div.player4").first()
                        if (player22Name != null) {
                            bean.player22 = translatePlayerName(player22Name.text())
                            bean.flag22 = player22.select("div.flag img").attr("src")
                            val player22Detail = lis[i - 1]
                            val player22Url = player22Detail.select("div.player2-name a")
                            if (player22Url != null && player22Url.size > 1) {
                                bean.player22Url = player22Url[1].attr("href")
                            }

                        }
                    }


                    bean.vs = li.select("div.vs").first().text()
                    bean.score = li.select("div.score").first().text().replace("Retired", "退赛")
                    val durationElement = li.select("div.timer1 span").first()
                    if (durationElement != null) {
                        if ("0:00" == durationElement.text()) {
                            bean.duration = "未开始"
                        } else {
                            bean.duration = "时长：" + durationElement.text()
                        }

                    } else {
                        bean.duration = "比赛中"
                    }
                    data.add(bean)
                    i += 2
                }
            }
        }

        val result=ArrayList<MatchDateBean>()
        val court1=data.filter { it.field=="场地1" }
        addNextTime(court1)
        result.addAll(court1)
        val court2=data.filter { it.field=="场地2" }
        addNextTime(court2)
        result.addAll(court2)
        val court3=data.filter { it.field=="场地3" }
        addNextTime(court3)
        result.addAll(court3)
        val court4=data.filter { it.field=="场地4" }
        addNextTime(court4)
        result.addAll(court4)
        val court5=data.filter { it.field=="场地5" }
        addNextTime(court5)
        result.addAll(court5)
        val courtN=data.filter { it.field=="流水场" }
        addNextTime(courtN)
        result.addAll(courtN)

        return result
    }
    private fun addNextTime(data:List<MatchDateBean>){
        for (index in data.indices){
            if(index>0) {
                if (data[index].time.isNullOrEmpty()){
                    data[index].time=estimateNextTime(data[index-1].time)
                }
            }
        }
    }

    private fun translateType(type: String): String {
        return when (type) {
            "MS" -> "男单"
            "WS" -> "女单"
            "MD" -> "男双"
            "WD" -> "女双"
            "XD" -> "混双"
            else -> type
        }
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

    private fun translateTime(time: String): String {
        var temp = time
        if (temp.contains("Starting at")) {
            temp = temp.replace("Starting at", "").trim { it <= ' ' }
            temp += " 开始"
        }
        return temp.replace("Followed by", "").replace("Not before", "").trim { it <= ' ' }
    }

    private fun getChineseTime(time: String?): String? {
        var time = time

        if (time == null) return time
        time = time.replace("AM", "")
        val m = Pattern.compile("\\d+:\\d+").matcher(time)
        if (m.find()) {
            val hs = m.group()
            val sdf = SimpleDateFormat("HH:mm")
            try {
                val date = sdf.parse(hs)
                val calendar = Calendar.getInstance()
                calendar.time = date
                if (time.contains("PM")) {
                    time = time.replace("PM", "")

                    if (calendar.get(Calendar.HOUR_OF_DAY) < 12) {
                        calendar.add(Calendar.HOUR_OF_DAY, 12)
                    }
                }
                calendar.add(Calendar.HOUR_OF_DAY, -timeDiffer)
                val newHs = sdf.format(calendar.time)
                return time.replace(hs, newHs)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

        }

        return time
    }
    private fun estimateNextTime(lastTime: String?):String{
        val m = Pattern.compile("\\d+:\\d+").matcher(lastTime)
        if (m.find()) {
            val hs = m.group()
            val sdf = SimpleDateFormat("HH:mm")
            try {
                val date = sdf.parse(hs)
                val calendar = Calendar.getInstance()
                calendar.time = date
                calendar.add(Calendar.MINUTE, nextMin)
                return sdf.format(calendar.time)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

        }
        return ""

    }

    private fun getParam(url: String?, name: String): String? {
        if (url != null) {
            val arr = url.split("\\?".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (arr != null && arr.size > 1) {
                val arr2 = arr[1].split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (arr2 != null) {
                    for (param in arr2) {
                        val arr3 = param.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        if (arr3 != null && arr3.size == 2) {
                            if (name == arr3[0]) {
                                return arr3[1]
                            }
                        }
                    }
                }
            }
        }
        return null
    }

    private fun getHandOffUrl(bean: MatchDateBean): String? {
        var url: String? = null
        if (!TextUtils.isEmpty(bean.player1Url) && !TextUtils.isEmpty(bean.player2Url)) {
            url = bean.player1Url + "/head-to-head-analysis/?team2_player1=" + getPlayerIdFromUrl(bean.player2Url)
            if (!TextUtils.isEmpty(bean.player12Url) && !TextUtils.isEmpty(bean.player22Url)) {
                url += "&event=double+&partner=" + getPlayerIdFromUrl(bean.player12Url) + "&team2_player2=" + getPlayerIdFromUrl(bean.player22Url)
            }
        }
        return url


    }

    private fun getPlayerIdFromUrl(playerUrl: String?): String? {
        val m = Pattern.compile("\\d+").matcher(playerUrl)
        return if (m.find()) {
            m.group()
        } else null
    }

}
