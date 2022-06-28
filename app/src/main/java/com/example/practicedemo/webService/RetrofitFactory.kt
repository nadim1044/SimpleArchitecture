package com.example.practicedemo.webService

import androidx.core.os.BuildCompat
import androidx.databinding.ktx.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory {

    companion object {


        val rectr by lazy {
            Retrofit.Builder()
                .baseUrl(com.example.practicedemo.BuildConfig.BASE_URL)
                .client(client())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitService::class.java)
        }

        fun client(): OkHttpClient {

            val logging = HttpLoggingInterceptor()

            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build()
            return client
        }

        fun get(): RetrofitService {
            return rectr
        }
    }
}