package com.leory.badminton.news.mvp.contract

import com.leory.badminton.news.mvp.model.bean.HandOffBean
import com.leory.commonlib.mvp.IModel
import com.leory.commonlib.mvp.IView

import io.reactivex.Observable

/**
 * Describe : 交手记录接口
 * Author : leory
 * Date : 2019-07-23
 */
interface HandOffRecordContract {
    interface View : IView {
        fun showHandOffView(bean: HandOffBean) //显示交手记录
    }

    interface Model : IModel {
        fun getHandOffRecords(url: String): Observable<String> //获取交手记录
    }
}
