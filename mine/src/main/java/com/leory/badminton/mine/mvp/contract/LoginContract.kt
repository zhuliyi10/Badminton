package com.leory.badminton.mine.mvp.contract

import com.leory.badminton.mine.mvp.model.bean.UserInfoBean
import com.leory.commonBusiness.http.BaseBusinessBean
import com.leory.commonlib.mvp.IModel
import com.leory.commonlib.mvp.IView

import io.reactivex.Observable

/**
 * Describe : 登陆接口
 * Author : leory
 * Date : 2019-07-11
 */
interface LoginContract {
    interface View : IView {
        fun loginSuccess(bean: UserInfoBean)
    }

    interface Model : IModel {
        fun login(phone: String, pwd: String): Observable<BaseBusinessBean<UserInfoBean>>
    }
}
