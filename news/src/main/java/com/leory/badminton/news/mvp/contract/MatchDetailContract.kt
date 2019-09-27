package com.leory.badminton.news.mvp.contract

import com.leory.badminton.news.mvp.model.bean.MatchDateBean
import com.leory.badminton.news.mvp.model.bean.MatchInfoBean
import com.leory.badminton.news.mvp.model.bean.MultiMatchHistoryBean
import com.leory.badminton.news.mvp.model.bean.MultiMatchPlayersBean
import com.leory.badminton.news.mvp.ui.widget.againstFlow.AgainstFlowBean
import com.leory.commonlib.mvp.IModel
import com.leory.commonlib.mvp.IView

import io.reactivex.Observable

/**
 * Describe : 赛事详情接口
 * Author : leory
 * Date : 2019-05-27
 */
interface MatchDetailContract {
    interface View : IView {

        fun showMatchInfo(bean: MatchInfoBean) //显示比赛信息

    }

    interface MatchAgainView : IView {
        fun showAgainstView(againstData: List<List<AgainstFlowBean>>) //显示对阵数据

        fun showMatchSchedule(tags: List<String>?)
    }

    interface MatchDateView : IView {
        val filterText: String//获取过虑文字
        fun showDateData(data: List<MatchDateBean>) //显示日期赛事
        fun toHistoryDetail(handOffUrl: String?) //跳到交手战绩
    }

    interface MatchHistory : IView {
        fun showHistoryData(data: List<MultiMatchHistoryBean<*>>)
    }

    interface MatchPlayersView : IView {
        fun showPlayersData(data: List<MultiMatchPlayersBean<*>>)
    }

    interface Model : IModel {
        fun getMatchInfo(url: String): Observable<String>

        fun getMatchDetail(url: String): Observable<String>

        fun getMatchDate(url: String): Observable<String>
        fun getMatchDate(url: String, match: String): Observable<String>

        fun getMatchHistory(url: String): Observable<String>

        fun getMatchPlayers(url: String, tab: String): Observable<String>
    }
}
