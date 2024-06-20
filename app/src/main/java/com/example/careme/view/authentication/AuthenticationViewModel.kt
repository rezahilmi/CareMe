package com.example.careme.view.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.careme.data.UserRepository
import com.example.careme.data.network.LoginResponse
import com.example.careme.data.network.dataModel.RegisterRequest
import com.example.careme.data.network.RegisterResponse
import com.example.careme.data.network.dataModel.LoginRequest
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AuthenticationViewModel (private val userRepository: UserRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _registerResult = MutableLiveData<RegisterResponse>()
    val registerResult: LiveData<RegisterResponse> = _registerResult

    fun register(registerRequest: RegisterRequest) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = userRepository.registerUser(registerRequest)
                _registerResult.value = response
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
                _registerResult.value = errorResponse
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun login(loginRequest: LoginRequest) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = userRepository.loginUser(loginRequest)
                _loginResult.value = response
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
//                val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
//                _loginResult.value = errorResponse
            } finally {
                _isLoading.value = false
            }
        }
    }

}