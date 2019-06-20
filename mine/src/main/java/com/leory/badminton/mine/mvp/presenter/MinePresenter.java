package com.leory.badminton.mine.mvp.presenter;

import com.leory.badminton.mine.mvp.contract.MineContract;
import com.leory.badminton.mine.mvp.model.bean.UserInfoBean;
import com.leory.badminton.mine.mvp.model.sp.AccountSp;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * Describe : 我的Presenter
 * Author : leory
 * Date : 2019-06-19
 */
@ActivityScope
public class MinePresenter extends BasePresenter<MineContract.Model, MineContract.View> {
    @Inject
    public MinePresenter(MineContract.Model model, MineContract.View rootView) {
        super(model, rootView);
        init();
    }

    private void init() {

    }
    public void updateLoginState(){
        boolean loginState=AccountSp.getLoginState();
        rootView.showLoginState(loginState,AccountSp.getUserInfoBean());
    }
}
