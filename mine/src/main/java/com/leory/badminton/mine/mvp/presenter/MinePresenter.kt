package com.leory.badminton.mine.mvp.presenter

import com.leory.badminton.mine.mvp.contract.MineContract
import com.leory.badminton.mine.mvp.model.sp.AccountSp
import com.leory.commonlib.di.scope.ActivityScope
import com.leory.commonlib.mvp.BasePresenter
import javax.inject.Inject

/**
 * Describe : 我的Presenter
 * Author : leory
 * Date : 2019-06-19
 */
@ActivityScope
class MinePresenter @Inject constructor(model: MineContract.Model, rootView: MineContract.View) :
        BasePresenter<MineContract.Model, MineContract.View>(model, rootView) {
    init {
        init()
    }

    private fun init() {

    }

    fun updateLoginState() {
        val loginState = AccountSp.getLoginState()
        rootView.showLoginState(loginState, AccountSp.getUserInfoBean())
    }
}
