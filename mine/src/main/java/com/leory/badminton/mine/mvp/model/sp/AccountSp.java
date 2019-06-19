package com.leory.badminton.mine.mvp.model.sp;

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
}
