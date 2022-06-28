package com.example.practicedemo.webService

import com.example.practicedemo.mvvm.model.response.LoginResponse
import com.example.practicedemo.mvvm.model.response.MovieResponseX
import com.example.practicedemo.mvvm.model.response.userlist
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @GET("filmShowTimes")
    suspend fun filmShowTimes(
        @Header("geolocation") geolocation : String,
        @Header("client") client: kotlin.String,
        @Header("x-api-key") apiKey: String,
        @Header("authorization") authorization: kotlin.String,
        @Header("territory") territory: kotlin.String,
        @Header("api-version") apiversion: String,
        @Header("device-datetime") dateTime: String,
        @Query("n") number: kotlin.Int? = null,
        @Query("film_id") filmID: kotlin.String,
        @Query("date") date: kotlin.String
    ): Response<MovieResponseX>




    @GET("users")
    suspend fun getPosts() : Response<userlist>



}