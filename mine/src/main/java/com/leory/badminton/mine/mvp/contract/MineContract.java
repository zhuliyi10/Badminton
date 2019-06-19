package com.leory.badminton.mine.mvp.contract;

import com.leory.commonlib.mvp.IModel;
import com.leory.commonlib.mvp.IView;

/**
 * Describe : 我的接口
 * Author : leory
 * Date : 2019-06-19
 */
public interface MineContract {
    interface View extends IView{
        void showLoginState(boolean isLogin);
    }
    interface Model extends IModel{

    }
}
