package com.leory.badminton.news.mvp.model.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Describe : 赛事api
 * Author : leory
 * Date : 2019-05-19
 */
public interface MatchApi {
    @GET
    Observable<Object>getMatchList(@Url String url,
                                   @Query("ajax")String ajax,
                                   @Query("ryear")String ryear,
                                   @Query("rstate")String rstate);
}
