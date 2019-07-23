package com.leory.badminton.news.di.module;

import com.leory.badminton.news.mvp.contract.HandOffRecordContract;
import com.leory.badminton.news.mvp.contract.LiveContract;
import com.leory.badminton.news.mvp.model.HandOffRecordModel;
import com.leory.badminton.news.mvp.model.LiveModel;

import dagger.Binds;
import dagger.Module;

/**
 * Describe : 交手记录
 * Author : leory
 * Date : 2019-07-23
 */
@Module
public abstract class HandOffRecordModule {
    @Binds
    abstract HandOffRecordContract.Model bindHandOffRecordModel(HandOffRecordModel model);

}
