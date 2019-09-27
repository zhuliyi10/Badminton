package com.leory.badminton.news.mvp.contract

import com.leory.badminton.news.mvp.model.bean.LiveBean
import com.leory.badminton.news.mvp.model.bean.LiveDetailBean
import com.leory.commonlib.mvp.IModel
import com.leory.commonlib.mvp.IView

import io.reactivex.Observable

/**
 * Describe :直播接口
 * Author : leory
 * Date : 2019-06-03
 */
interface LiveContract {
    interface View : IView {
        fun showLiveData(bean: LiveBean)
        fun showLiveDetail(data: List<LiveDetailBean>) //显示直播列表
    }

    interface Model : IModel {
        val liveMatch: Observable<String>
        fun getLiveDetail(url: String?): Observable<String>
        fun getLiveUrl(url: String): Observable<String>
    }
}
