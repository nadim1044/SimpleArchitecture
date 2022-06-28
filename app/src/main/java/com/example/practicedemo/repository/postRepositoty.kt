package com.example.practicedemo.repository

import com.example.practicedemo.mvvm.model.response.userlist
import com.example.practicedemo.webService.RetrofitService
import retrofit2.Response

class PostRepositoty(private val retrofitService: RetrofitService) {

    suspend fun getpost() : Response<userlist> {
        return retrofitService.getPosts()
    }

}