package com.leory.badminton.mine.mvp.presenter

import com.leory.badminton.mine.mvp.contract.LoginContract
import com.leory.badminton.mine.mvp.model.bean.UserInfoBean
import com.leory.commonBusiness.http.BaseBusinessBean
import com.leory.commonBusiness.http.RxBusinessHandlerSubscriber
import com.leory.commonlib.di.scope.ActivityScope
import com.leory.commonlib.mvp.BasePresenter
import com.leory.commonlib.utils.RxUtils
import javax.inject.Inject

/**
 * Describe : 登陆presenter
 * Author : leory
 * Date : 2019-07-11
 */
@ActivityScope
class LoginPresenter @Inject constructor(model: LoginContract.Model, rootView: LoginContract.View) :
        BasePresenter<LoginContract.Model, LoginContract.View>(model, rootView) {


    fun login(phone: String, pwd: String) {
        model.login(phone, pwd)
                .compose<BaseBusinessBean<UserInfoBean>>(RxUtils.applySchedulersDelayFinal<BaseBusinessBean<UserInfoBean>>(rootView))
                .subscribe(object : RxBusinessHandlerSubscriber<UserInfoBean>() {

                    override fun onSuccess(data: UserInfoBean?) {
                        rootView.loginSuccess(data)
                    }

                    override fun onFail(errorMsg: String) {
                        rootView.showMessage(errorMsg)
                    }
                })
    }
}
