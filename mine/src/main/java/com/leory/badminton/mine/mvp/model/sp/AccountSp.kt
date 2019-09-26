package com.leory.badminton.mine.mvp.model.sp

import android.text.TextUtils

import com.google.gson.Gson
import com.leory.badminton.mine.mvp.model.bean.UserInfoBean
import com.leory.commonlib.utils.SPUtils

/**
 * Describe : 保存帐号信息
 * 只需要在本类中定义表名，和属性名便可以进行操作
 * Author : leory
 * Date : 2019-06-19
 */
object AccountSp {
    /**
     * 表名
     */
    private val SP_TABLE_NAME = "account_db"
    private val KEY_USER_INFO = "key_user_info"//用户信息
    private val KEY_LOGIN_STATE = "key_login_state"//登陆状态

    /**
     * 获取sp工具类
     *
     * @return
     */
    private val spUtils: SPUtils
        get() = SPUtils.getInstance(SP_TABLE_NAME)

    var loginState: Boolean
        get() = spUtils.getBoolean(KEY_LOGIN_STATE, false)
        set(state) {
            spUtils.put(KEY_LOGIN_STATE, state)
        }

    var userInfoBean: UserInfoBean?
        get() {
            val userStr = spUtils.getString(KEY_USER_INFO, "")
            return if (TextUtils.isEmpty(userStr)) {
                null
            } else {
                Gson().fromJson(userStr, UserInfoBean::class.java)
            }
        }
        set(bean) {
            bean?.let {
                val userStr = Gson().toJson(it)
                spUtils.put(KEY_USER_INFO, userStr)
            }

        }
}


