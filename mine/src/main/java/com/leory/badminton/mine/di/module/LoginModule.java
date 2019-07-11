package com.leory.badminton.mine.di.module;

import com.leory.badminton.mine.mvp.contract.LoginContract;
import com.leory.badminton.mine.mvp.contract.MineContract;
import com.leory.badminton.mine.mvp.model.LoginModel;
import com.leory.badminton.mine.mvp.model.MineModel;

import dagger.Binds;
import dagger.Module;

/**
 * Describe : 登陆Module
 * Author : leory
 * Date : 2019-07-11
 */
@Module
public abstract class LoginModule {
    @Binds
    abstract LoginContract.Model bindLoginModel(LoginModel model);
}
