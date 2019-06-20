package com.leory.badminton.mine.mvp.model.sp;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.leory.badminton.mine.mvp.model.bean.UserInfoBean;
import com.leory.commonlib.utils.SPUtils;

/**
 * Describe : 保存帐号信息
 * 只需要在本类中定义表名，和属性名便可以进行操作
 * Author : leory
 * Date : 2019-06-19
 */
public class AccountSp {
    /**
     * 表名
     */
    private static String SP_TABLE_NAME = "account_db";
    private static final String KEY_USER_INFO = "key_user_info";//用户信息
    private static final String KEY_LOGIN_STATE = "key_login_state";//登陆状态

    /**
     * 获取sp工具类
     *
     * @return
     */
    public static SPUtils getSPUtils() {
        return SPUtils.getInstance(SP_TABLE_NAME);
    }

    public static boolean getLoginState() {
        return getSPUtils().getBoolean(KEY_LOGIN_STATE, false);
    }

    public static void putLoginState(boolean state) {
        getSPUtils().put(KEY_LOGIN_STATE, state);
    }

    public static UserInfoBean getUserInfoBean() {
        String userStr = getSPUtils().getString(KEY_USER_INFO, "");
        if (TextUtils.isEmpty(userStr)) {
            return null;
        } else {
            return new Gson().fromJson(userStr, UserInfoBean.class);
        }
    }

    public static void putUserInfoBean(UserInfoBean bean) {
        String userStr = new Gson().toJson(bean);
        getSPUtils().put(KEY_USER_INFO, userStr);
    }
}
