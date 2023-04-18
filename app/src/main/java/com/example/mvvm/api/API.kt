package com.example.mvvm.api

import com.example.mvvm.model.login.LoginModel
import com.example.mvvm.model.details.Details
import com.example.mvvm.model.details.DetailsData
import com.example.mvvm.model.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface API {
    @POST("authaccount/login")
    fun loginUser(@Body registerData: LoginModel): Call<LoginResponse>

    @GET("users")
    fun getInfo(@Header("Authorization") token:String): Call<Details>

}