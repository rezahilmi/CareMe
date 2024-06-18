package com.example.careme.view.prediction

import androidx.lifecycle.ViewModel
import com.example.careme.data.UserRepository

class PredictionViewModel(private val repository: UserRepository) : ViewModel() {
//    private val _uploadResult = MutableLiveData<ResultState<UploadStoryResponse>>()
//    val uploadResult: LiveData<ResultState<UploadStoryResponse>> = _uploadResult
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    fun uploadImage(imageFile: File) {
//        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
//        val multipartBody = MultipartBody.Part.createFormData(
//            "photo",
//            imageFile.name,
//            requestImageFile
//        )
//
//        _uploadResult.value = ResultState.Loading
//
//        viewModelScope.launch {
//            try {
//                val response = repository.uploadImage(multipartBody)
//                _uploadResult.value = ResultState.Success(response)
//            } catch (e: HttpException) {
//                val errorBody = e.response()?.errorBody()?.string()
//                val errorResponse = Gson().fromJson(errorBody, UploadStoryResponse::class.java)
//                _uploadResult.value = ResultState.Error(errorResponse.message ?: "Unknown error")
//            }
//        }
//    }
}