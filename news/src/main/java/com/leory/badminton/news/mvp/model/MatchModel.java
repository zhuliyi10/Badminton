package com.leory.badminton.news.mvp.model;

import com.leory.badminton.news.mvp.contract.MatchContract;
import com.leory.badminton.news.mvp.model.api.MatchApi;
import com.leory.commonlib.di.scope.FragmentScope;
import com.leory.commonlib.http.IRepositoryManager;
import com.leory.commonlib.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Describe :赛事model
 * Author : leory
 * Date : 2019-05-19
 */
@FragmentScope
public class MatchModel extends BaseModel implements MatchContract.Model {
    private static String url = "https://bwfbadminton.cn/calendar/";
    private static String ajax = "bwfresultlanding";

    @Inject
    public MatchModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<String> getMatchList(String ryear, String rstate) {
        return repositoryManager.obtainRetrofitService(MatchApi.class)
                .getMatchList(url + ryear + "/" + rstate + "/", ajax, ryear, rstate);
    }
}
