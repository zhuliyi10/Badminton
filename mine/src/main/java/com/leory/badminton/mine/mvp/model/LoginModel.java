package com.leory.badminton.mine.mvp.model;

import com.leory.badminton.mine.mvp.contract.LoginContract;
import com.leory.badminton.mine.mvp.contract.MineContract;
import com.leory.badminton.mine.mvp.model.api.LoginApi;
import com.leory.badminton.mine.mvp.model.bean.UserInfoBean;
import com.leory.commonBusiness.http.BaseBusinessBean;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.http.IRepositoryManager;
import com.leory.commonlib.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Describe : 登陆 model
 * Author : leory
 * Date : 2019-07-11
 */
@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {
    String url="http://192.168.0.92:8000/login/";
    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBusinessBean<UserInfoBean>> login(String phone, String pwd) {
        return repositoryManager.obtainRetrofitService(LoginApi.class).login(url,phone,pwd);
    }
}
