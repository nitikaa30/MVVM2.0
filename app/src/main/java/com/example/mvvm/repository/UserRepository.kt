package com.example.mvvm.repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.mvvm.model.login.LoginModel
import com.example.mvvm.model.details.DetailsData
import com.example.mvvm.model.details.Details
import com.example.mvvm.model.login.LoginResponse
import com.example.mvvm.retrofit.Retrofit


sealed class ApiResponse<out T> {
    data class Progress(val loading:Boolean):ApiResponse<Nothing>()
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String) : ApiResponse<Nothing>()
}

class UserRepository {
    fun login(login: LoginModel, callback: (ApiResponse<LoginResponse>) -> Unit) {
        Retrofit.apiInterface.loginUser(login).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    response.body()?.let {
                        if (it.code == 0) {
                            callback(ApiResponse.Success(it))
                        } else {
                            val errorBody = response.errorBody()?.string() ?: "Unknown error"
                            callback(ApiResponse.Error(errorBody))
                        }
                    } ?: callback(ApiResponse.Error("Unknown error"))
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    callback(ApiResponse.Error(errorBody))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback(ApiResponse.Error(t.message ?: "Unknown error"))
            }
        })
    }


    fun getUsers(token: String, callback: (ApiResponse<List<DetailsData>>) -> Unit) {
        Retrofit.apiInterface.getInfo("Bearer $token").enqueue(object : Callback<Details> {
            override fun onResponse(
                call: Call<Details>,
                response: Response<Details>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    response.body()?.let {
                        val list = it.data
                        callback(ApiResponse.Success(list))
                    } ?: callback(ApiResponse.Error("Unknown error"))
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    callback(ApiResponse.Error(errorBody))
                }
            }

            override fun onFailure(call: Call<Details>, t: Throwable) {
                callback(ApiResponse.Error(t.message ?: "Unknown error"))
            }
        })
    }
}


//class UserRepository() {
//    private lateinit var context: Context
//
//
//    fun login(login: LoginModel, callback: (LoginResponse?) -> Unit) {
//        Retrofit.apiInterface.loginUser(login).enqueue(object : Callback<LoginResponse> {
//            override fun onResponse(
//                call: Call<LoginResponse>,
//                response: Response<LoginResponse>
//            ) {
//                if (response.isSuccessful&&response.code()==200) {
//                    if(response.body()?.code==0){
//                        callback(response.body())
//                    }
//                    else{
//                        Toast.makeText(context, "${response.errorBody()}", Toast.LENGTH_SHORT).show()
//                    }
//
//                } else {
//                    callback(null)
//                }
//            }
//
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                callback(null)
//            }
//        })
//    }
//
//    fun getUsers(token: String, callback: (List<DataX>?) -> Unit) {
//        Retrofit.apiInterface.getInfo("Bearer $token").enqueue(object : Callback<Details> {
//            override fun onResponse(
//                call: Call<Details>,
//                response: Response<Details>
//            ) {
//                if (response.isSuccessful&& response.code()==200) {
//                    val list: ArrayList<DataX>?=response.body()?.data
//                    callback(list)
//                } else {
//                    Toast.makeText(context,"${response.errorBody()}",Toast.LENGTH_SHORT).show()
//                    callback(null)
//                }
//            }
//
//            override fun onFailure(call: Call<Details>, t: Throwable) {
//                callback(null)
//            }
//        })
//    }
//
//
//}