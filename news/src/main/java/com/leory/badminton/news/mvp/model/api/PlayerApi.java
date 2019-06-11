package com.leory.badminton.news.mvp.model.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Describe : 运动员接口
 * Author : leory
 * Date : 2019-06-11
 */
public interface PlayerApi {
    @GET
    Observable<String> getPlayerDetail(@Url String url);//获取运动员详情

    @GET
    Observable<String> getPlayerMatch(@Url String url, @Query("year") String year);//获取运动员赛果
}
