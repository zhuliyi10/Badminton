package com.leory.badminton.news.mvp.model;

import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.model.api.MatchApi;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.http.IRepositoryManager;
import com.leory.commonlib.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Describe :赛事详情model
 * Author : leory
 * Date : 2019-05-27
 */
@ActivityScope
public class MatchDetailModel extends BaseModel implements MatchDetailContract.Model {

    private String ajaxTmt = "bwfdraw";
    private String bwfdate = "bwfdate";

    @Inject
    public MatchDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<String> getMatchInfo(String url) {
        return repositoryManager.obtainRetrofitService(MatchApi.class).getMatchInfo(url);
    }

    @Override
    public Observable<String> getMatchDetail(String url) {
        return repositoryManager.obtainRetrofitService(MatchApi.class).getMatchDetail(url, ajaxTmt);
    }

    @Override
    public Observable<String> getMatchDate(String url) {
        return repositoryManager.obtainRetrofitService(MatchApi.class).getMatchDate(url, bwfdate);
    }
}
