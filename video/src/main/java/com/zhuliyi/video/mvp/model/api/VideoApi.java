package com.zhuliyi.video.mvp.model.api;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Describe : 视频api
 * Author : zhuly
 * Date : 2019-05-14
 */
public interface VideoApi {
    @FormUrlEncoded
    @POST
    Observable<Object>getVideoList(@Url String url,@Field("k")String k, @Field("n")int n, @Field("rnd")String rnd, @Field("_csrf")String _csrf);
}
