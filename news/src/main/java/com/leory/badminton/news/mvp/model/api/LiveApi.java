package com.leory.badminton.news.mvp.model.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Describe : 直播api
 * Author : leory
 * Date : 2019-06-03
 */
public interface LiveApi {

    @GET
    Observable<String>getLiveMatch(@Url String url);//获取直播比赛
}
