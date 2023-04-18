package com.example.mvvm.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.example.mvvm.api.API

object Retrofit {
    val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder().addInterceptor(interceptor)
        .connectTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .build()
    private val retrofit by lazy{
        Retrofit.Builder().client(client).baseUrl("http://restapi.adequateshop.com/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    val apiInterface by lazy{
        retrofit.create(API::class.java)
    }
}