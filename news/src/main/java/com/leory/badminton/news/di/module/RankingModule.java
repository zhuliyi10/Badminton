package com.leory.badminton.news.di.module;

import com.leory.badminton.news.mvp.contract.RankingContract;
import com.leory.badminton.news.mvp.model.RankingModel;

import dagger.Binds;
import dagger.Module;

/**
 * Describe : 排名module
 * Author : leory
 * Date : 2019-05-21
 */
@Module
public abstract class RankingModule {
    @Binds
    abstract RankingContract.Model bindMatchModel(RankingModel model);
}
