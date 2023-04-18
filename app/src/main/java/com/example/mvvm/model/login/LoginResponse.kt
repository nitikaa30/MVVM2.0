package com.example.mvvm.model.login
import com.google.gson.annotations.SerializedName

data class LoginResponse(val code: Int,
                         val `data`: Data?,
                         val message: String){
    data class Data(@SerializedName("email")
                    val Email: String,
                    @SerializedName("id")
                    val Id: Int,

                    val Name: String,

                    val Token: String)
}
