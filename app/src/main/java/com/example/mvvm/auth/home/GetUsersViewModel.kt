package com.example.mvvm.auth.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.model.details.DetailsData
import com.example.mvvm.repository.ApiResponse
import com.example.mvvm.repository.UserRepository

class GetUsersViewModel() : ViewModel() {
    val userRepository = UserRepository()

    private val _users = MutableLiveData<ApiResponse<List<DetailsData>>?>()
    val users: LiveData<ApiResponse<List<DetailsData>>?>
        get() = _users

    fun fetchUsers(token: String) {
        userRepository.getUsers("Bearer $token") { userList ->
            _users.postValue(userList)
        }
    }
}