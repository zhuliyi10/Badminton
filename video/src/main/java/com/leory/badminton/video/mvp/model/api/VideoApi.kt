package com.leory.badminton.video.mvp.model.api

import com.leory.badminton.video.mvp.model.bean.VideoBaseResponse
import com.leory.badminton.video.mvp.model.bean.VideoListBean

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * Describe : 视频api
 * Author : zhuly
 * Date : 2019-05-14
 */
interface VideoApi {
    @Headers("User-Agent:Mozilla/5.0 (Linux; Android 8.0.0; Mi Note 2 Build/OPR1.170623.032; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/62.0.3202.84 Mobile Safari/537.36", "Cookie:_csrf=mlZgSDcjdlUJ2eWO_5FpXofvl7ctr8uu;grwng_uid=fba2b068-c6f4-47c7-8066-60e8d7691e0e;gr_user_id=683efa9b-4eb6-4283-aabd-b1286a8c135d;token=038a0dcf66406659a64f77b5cfa2a3da%2A2883268;sign=b8b7b91f7fe5b69085e42821f3c8b1fe;ep=NjA0ODAw;oid=34aac6ea287ac9e9c742aac1be450fc5")
    @FormUrlEncoded
    @POST
    fun getVideoList(@Url url: String, @Field("k") k: String, @Field("n") n: Int, @Field("rnd") rnd: String, @Field("_csrf") _csrf: String): Observable<VideoBaseResponse<VideoListBean>>
}
