package com.leory.badminton.news.di.module;

import com.leory.badminton.news.mvp.contract.MatchContract;
import com.leory.badminton.news.mvp.model.MatchModel;

import dagger.Binds;
import dagger.Module;

/**
 * Describe : 赛事module
 * Author : leory
 * Date : 2019-05-19
 */
@Module
public abstract class MatchModule {
    @Binds
    abstract MatchContract.Model bindMatchModel(MatchModel model);

}
