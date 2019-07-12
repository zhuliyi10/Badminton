package com.leory.badminton.mine.mvp.presenter;

import com.leory.badminton.mine.mvp.contract.LoginContract;
import com.leory.badminton.mine.mvp.model.bean.UserInfoBean;
import com.leory.commonBusiness.http.RxBusinessHandlerSubscriber;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.http.RxHandlerSubscriber;
import com.leory.commonlib.mvp.BasePresenter;
import com.leory.commonlib.utils.RxUtils;

import javax.inject.Inject;

/**
 * Describe : 登陆presenter
 * Author : leory
 * Date : 2019-07-11
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }


    public void login(String phone, String pwd) {
        model.login(phone, pwd)
                .compose(RxUtils.applySchedulersDelayFinal(rootView))
                .subscribe(new RxBusinessHandlerSubscriber<UserInfoBean>() {
                    @Override
                    public void onSuccess(UserInfoBean data) {
                        rootView.loginSuccess(data);
                    }

                    @Override
                    public void onFail(String errorMsg) {
                        rootView.showMessage(errorMsg);
                    }
                });
    }
}
