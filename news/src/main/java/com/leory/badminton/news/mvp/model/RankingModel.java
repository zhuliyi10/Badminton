package com.leory.badminton.news.mvp.model;

import android.text.TextUtils;

import com.leory.badminton.news.mvp.contract.RankingContract;
import com.leory.badminton.news.mvp.model.api.RankingApi;
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
public class RankingModel extends BaseModel implements RankingContract.Model {
    private static String url = "https://bwfbadminton.cn/rankings/2/bwf-world-rankings/";

    @Inject
    public RankingModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<String> getRankingList(String rankingType, String week, int pageNum, int num) {
        StringBuffer urlBuffer = new StringBuffer().append(url);
        if (!TextUtils.isEmpty(rankingType)) {
            urlBuffer.append(rankingType);
        }
        if (!TextUtils.isEmpty(week)) {
            urlBuffer.append(week);
        }
        return repositoryManager.obtainRetrofitService(RankingApi.class).getRankingList(urlBuffer.toString(), pageNum, num);
    }
}
