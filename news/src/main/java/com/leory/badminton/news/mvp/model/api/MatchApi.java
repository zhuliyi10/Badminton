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
    Observable<String>getMatchList(@Url String url,
                                   @Query("ajax")String ajax,
                                   @Query("ryear")String ryear,
                                   @Query("rstate")String rstate);

    @GET
    Observable<String>getMatchDetail(@Url String url,@Query("ajaxTmt")String ajaxTmt);//获取对阵信息
    @GET
    Observable<String>getMatchInfo(@Url String url);//获取比赛信息
    @GET
    Observable<String>getMatchHistory(@Url String url);//获取历史比赛
    @GET
    Observable<String>getMatchDate(@Url String url,@Query("ajaxTmt")String ajaxTmt);//获取比赛赛程
    @GET
    Observable<String>getMatchDate(@Url String url,@Query("match")String match,@Query("ajax")String ajax,@Query("stab")String stab);//获取比赛赛程
    @GET
    Observable<String>getMatchPlayers(@Url String url,@Query("tab")String tab);//获取参赛运动员
}
