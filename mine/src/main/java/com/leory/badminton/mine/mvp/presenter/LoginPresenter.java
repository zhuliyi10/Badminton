package com.leory.badminton.mine.mvp.presenter;

import com.leory.badminton.mine.mvp.contract.LoginContract;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.http.RxHandlerSubscriber;
import com.leory.commonlib.mvp.BasePresenter;
import com.leory.commonlib.utils.RxLifecycleUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        model.login(phone,pwd)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    rootView.showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    rootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new RxHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        rootView.showMessage(s);
                    }
                });
//        if (phone.equals("13250751496") && pwd.equals("123")) {
//            UserInfoBean bean = new UserInfoBean();
//            bean.setPhone(phone);
//            rootView.loginSuccess(bean);
//        } else {
//            rootView.showMessage("电话或密码错误");
//        }
    }
}
