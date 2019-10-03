package com.leory.badminton.news.mvp.model.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Describe : 运动员接口
 * Author : leory
 * Date : 2019-06-11
 */
interface PlayerApi {
    @GET
    fun getPlayerDetail(@Url url: String): Observable<String> //获取运动员详情

    @GET
    fun getPlayerMatch(@Url url: String, @Query("year") year: String?): Observable<String> //获取运动员赛果

    @GET
    fun getHandOffRecords(@Url url: String): Observable<String> //获取运动员交手记录
}
