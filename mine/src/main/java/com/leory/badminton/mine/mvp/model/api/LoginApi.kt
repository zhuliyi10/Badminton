package com.leory.badminton.mine.mvp.model.api

import com.leory.badminton.mine.mvp.model.bean.UserInfoBean
import com.leory.commonBusiness.http.BaseBusinessBean

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Describe : 登陆api
 * Author : leory
 * Date : 2019-07-11
 */
interface LoginApi {
    @POST
    @FormUrlEncoded
    fun login(@Url url: String, @Field("phone") phone: String, @Field("pwd") pwd: String): Observable<BaseBusinessBean<UserInfoBean>>

    @GET
    fun test(@Url url: String, @Query("phone") phone: String, @Query("pwd") pwd: String): Observable<String>
}
