package com.leory.badminton.news.mvp.model;

import com.leory.badminton.news.mvp.contract.LiveContract;
import com.leory.badminton.news.mvp.contract.MatchContract;
import com.leory.badminton.news.mvp.model.api.LiveApi;
import com.leory.badminton.news.mvp.model.api.MatchApi;
import com.leory.commonlib.di.scope.FragmentScope;
import com.leory.commonlib.http.IRepositoryManager;
import com.leory.commonlib.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Describe :直播model
 * Author : leory
 * Date : 2019-05-19
 */
@FragmentScope
public class LiveModel extends BaseModel implements LiveContract.Model {
    private static String url = "https://bwfworldtour.bwfbadminton.com/live/";


    private static int ajaxSchedule=1;
    @Inject
    public LiveModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<String> getLiveMatch() {
        return repositoryManager.obtainRetrofitService(LiveApi.class).getLiveMatch(url);
    }

    @Override
    public Observable<String> getLiveDetail(String url) {
        return repositoryManager.obtainRetrofitService(LiveApi.class).getLiveDetail(url,ajaxSchedule);
    }

    @Override
    public Observable<String> getLiveUrl(String url) {
        return repositoryManager.obtainRetrofitService(LiveApi.class).getLiveUrl(url);
    }
}
