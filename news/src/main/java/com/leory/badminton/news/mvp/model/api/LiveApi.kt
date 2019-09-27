package com.leory.badminton.news.mvp.model.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Describe : 直播api
 * Author : leory
 * Date : 2019-06-03
 */
interface LiveApi {

    @GET
    fun getLiveMatch(@Url url: String): Observable<String> //获取直播比赛

    @GET
    fun getLiveUrl(@Url url: String): Observable<String> //获取比赛地址

    @GET
    fun getLiveDetail(@Url url: String, @Query("ajaxSchedule") ajaxSchedule: Int): Observable<String> //获取直播比赛
}
