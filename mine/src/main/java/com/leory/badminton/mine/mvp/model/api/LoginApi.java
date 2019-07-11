package com.leory.badminton.mine.mvp.model.api;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Describe : 登陆api
 * Author : leory
 * Date : 2019-07-11
 */
public interface LoginApi {
    @POST()
    @FormUrlEncoded
    Observable<String> login(@Url String url, @Field("phone") String phone, @Field("pwd") String pwd);
}
