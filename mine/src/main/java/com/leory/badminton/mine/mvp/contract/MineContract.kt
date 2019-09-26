package com.leory.badminton.mine.mvp.contract

import com.leory.badminton.mine.mvp.model.bean.UserInfoBean
import com.leory.commonlib.mvp.IModel
import com.leory.commonlib.mvp.IView

/**
 * Describe : 我的接口
 * Author : leory
 * Date : 2019-06-19
 */
interface MineContract {
    interface View : IView {
        fun showLoginState(isLogin: Boolean, bean: UserInfoBean?)
    }

    interface Model : IModel
}
