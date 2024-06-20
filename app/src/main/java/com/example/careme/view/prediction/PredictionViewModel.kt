package com.example.careme.view.prediction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.careme.data.UserRepository
import com.example.careme.data.network.PredictionResponse
import com.example.careme.data.ResultState
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class PredictionViewModel(private val repository: UserRepository) : ViewModel() {
    private val _predictResult = MutableLiveData<ResultState<PredictionResponse>>()
    val predictResult: LiveData<ResultState<PredictionResponse>> = _predictResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun predict(imageFile: File) {
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "image",
            imageFile.name,
            requestImageFile
        )

        _predictResult.value = ResultState.Loading

        viewModelScope.launch {
            try {
                val response = repository.predict(multipartBody)
                _predictResult.value = ResultState.Success(response)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
//                val errorResponse = Gson().fromJson(errorBody, PredictionResponse::class.java)
                _predictResult.value = ResultState.Error( "Unknown error")
//                _predictResult.value = ResultState.Error(errorResponse.message ?: "Unknown error")
            }
        }
    }
}