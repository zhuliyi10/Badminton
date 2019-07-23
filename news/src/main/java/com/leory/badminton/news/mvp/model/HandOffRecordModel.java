package com.leory.badminton.news.mvp.model;

import com.leory.badminton.news.mvp.contract.HandOffRecordContract;
import com.leory.badminton.news.mvp.model.api.PlayerApi;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.http.IRepositoryManager;
import com.leory.commonlib.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Describe : 交手记录model
 * Author : leory
 * Date : 2019-07-23
 */
@ActivityScope
public class HandOffRecordModel extends BaseModel implements HandOffRecordContract.Model {
    @Inject
    public HandOffRecordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<String> getHandOffRecords(String url) {
        return repositoryManager.obtainRetrofitService(PlayerApi.class).getHandOffRecords(url);
    }
}
