package com.leory.badminton.news.mvp.model.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Describe : 排名api
 * Author : leory
 * Date : 2019-05-21
 */
interface RankingApi {
    @GET
    fun getRankingList(@Url url: String,
                       @Query("rows") rows: Int,
                       @Query("page_no") page_no: Int): Observable<String>
}
