package com.example.mvvm.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.model.login.LoginModel
import com.example.mvvm.model.login.LoginResponse
import com.example.mvvm.repository.ApiResponse
import com.example.mvvm.repository.UserRepository

class LoginViewModel() : ViewModel() {

    private val userRepository = UserRepository()

    private val _loginResult = MutableLiveData<ApiResponse<LoginResponse>?>()
    val loginResult: LiveData<ApiResponse<LoginResponse>?>
        get() = _loginResult

    fun login(login: LoginModel) {
        userRepository.login(login) { loginResponse ->
            _loginResult.postValue(loginResponse)
        }
    }
}
