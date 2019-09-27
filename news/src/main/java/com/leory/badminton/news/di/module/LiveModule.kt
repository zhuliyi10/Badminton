package com.leory.badminton.news.di.module

import com.leory.badminton.news.mvp.contract.LiveContract
import com.leory.badminton.news.mvp.contract.MatchContract
import com.leory.badminton.news.mvp.model.LiveModel
import com.leory.badminton.news.mvp.model.MatchModel

import dagger.Binds
import dagger.Module

/**
 * Describe : 直播module
 * Author : leory
 * Date : 2019-06-03
 */
@Module
abstract class LiveModule {
    @Binds
    internal abstract fun bindLiveModel(model: LiveModel): LiveContract.Model

}
