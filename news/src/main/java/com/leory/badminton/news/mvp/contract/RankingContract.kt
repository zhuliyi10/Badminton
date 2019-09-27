package com.leory.badminton.news.mvp.contract

import com.leory.badminton.news.mvp.model.bean.RankingBean
import com.leory.commonlib.mvp.IModel
import com.leory.commonlib.mvp.IView

import io.reactivex.Observable

/**
 * Describe : 排名接口
 * Author : leory
 * Date : 2019-05-19
 */
interface RankingContract {
    interface View : IView {
        fun startLoadMore()

        fun endLoadMore()

        fun showRankingData(refresh: Boolean, data: List<RankingBean>)

        fun showWeekData(data: List<String>)
    }

    interface Model : IModel {
        fun getRankingList(rankingType: String?, week: String?, pageNum: Int, num: Int): Observable<String>
    }
}
