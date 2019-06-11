package com.leory.badminton.news.mvp.model;

import com.leory.badminton.news.mvp.contract.PlayerContract;
import com.leory.badminton.news.mvp.model.api.PlayerApi;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.http.IRepositoryManager;
import com.leory.commonlib.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Describe : 运动员model
 * Author : leory
 * Date : 2019-06-11
 */
@ActivityScope
public class PlayerModel extends BaseModel implements PlayerContract.Model {
    @Inject
    public PlayerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<String> getPlayerDetail(String url) {
        return repositoryManager.obtainRetrofitService(PlayerApi.class)
                .getPlayerDetail(url);
    }

    @Override
    public Observable<String> getPlayerMatches(String url, String year) {
        return repositoryManager.obtainRetrofitService(PlayerApi.class)
                .getPlayerMatch(url, year);
    }
}
