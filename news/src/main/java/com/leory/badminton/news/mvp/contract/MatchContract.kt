package com.leory.badminton.news.mvp.contract

import com.leory.badminton.news.mvp.model.bean.MatchItemSection
import com.leory.commonlib.mvp.IModel
import com.leory.commonlib.mvp.IView

import org.jsoup.nodes.Document

import io.reactivex.Observable

/**
 * Describe : 赛事接口
 * Author : leory
 * Date : 2019-05-19
 */
interface MatchContract {
    interface View : IView {
        fun showMatchData(data: List<MatchItemSection>)
    }

    interface Model : IModel {
        fun getMatchList(ryear: String, rstate: String): Observable<String>
    }
}
