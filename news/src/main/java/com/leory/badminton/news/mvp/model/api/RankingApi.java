package com.leory.badminton.news.mvp.model.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Describe : 排名api
 * Author : leory
 * Date : 2019-05-21
 */
public interface RankingApi {
    @GET
    Observable<String>getRankingList(@Url String url,
                                   @Query("rows") int rows,
                                   @Query("page_no") int page_no);
}
