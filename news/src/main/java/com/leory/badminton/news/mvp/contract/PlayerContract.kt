package com.leory.badminton.news.mvp.contract

import com.leory.badminton.news.mvp.model.bean.PlayerDetailBean
import com.leory.badminton.news.mvp.model.bean.PlayerMatchBean
import com.leory.commonlib.mvp.IModel
import com.leory.commonlib.mvp.IView

import io.reactivex.Observable

/**
 * Describe : 运动员接口
 * Author : leory
 * Date : 2019-06-11
 */
interface PlayerContract {
    interface View : IView {
        fun showPlayerDetail(bean: PlayerDetailBean)
    }

    interface MatchView : IView {
        fun showMatchData(data: List<PlayerMatchBean>)
    }

    interface Model : IModel {
        fun getPlayerDetail(url: String): Observable<String>

        fun getPlayerMatches(url: String, year: String?): Observable<String>
    }
}
