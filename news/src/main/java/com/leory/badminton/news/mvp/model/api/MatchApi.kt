package com.leory.badminton.news.mvp.model.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Describe : 赛事api
 * Author : leory
 * Date : 2019-05-19
 */
interface MatchApi {
    @GET
    fun getMatchList(@Url url: String,
                     @Query("ajax") ajax: String,
                     @Query("ryear") ryear: String,
                     @Query("rstate") rstate: String): Observable<String>

    @GET
    fun getMatchDetail(@Url url: String, @Query("ajaxTmt") ajaxTmt: String): Observable<String> //获取对阵信息

    @GET
    fun getMatchInfo(@Url url: String): Observable<String> //获取比赛信息

    @GET
    fun getMatchHistory(@Url url: String): Observable<String> //获取历史比赛

    @GET
    fun getMatchDate(@Url url: String, @Query("ajaxTmt") ajaxTmt: String): Observable<String> //获取比赛赛程

    @GET
    fun getMatchDate(@Url url: String, @Query("match") match: String, @Query("ajax") ajax: String, @Query("stab") stab: String): Observable<String> //获取比赛赛程

    @GET
    fun getMatchPlayers(@Url url: String, @Query("tab") tab: String): Observable<String> //获取参赛运动员
}
