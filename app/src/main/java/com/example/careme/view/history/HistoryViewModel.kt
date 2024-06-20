package com.example.careme.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.careme.data.UserRepository
import com.example.careme.data.network.ResultItem
import com.example.careme.data.network.SpecificHistoryResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HistoryViewModel(private val repository: UserRepository) : ViewModel() {

    var historyList: LiveData<PagingData<ResultItem>> =
        repository.getHistory().cachedIn(viewModelScope)

    private val _historyDetail = MutableLiveData<SpecificHistoryResponse>()
    val historyDetail: LiveData<SpecificHistoryResponse> = _historyDetail


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchDetailHistory(id: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getSpecificHistory(id)
                _historyDetail.value = response
            } catch (e: HttpException) {
                val response = repository.getSpecificHistory(id)
                _historyDetail.value = response
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, SpecificHistoryResponse::class.java)
                _historyDetail.value = errorResponse
            } finally {
                _isLoading.value = false
            }
        }
    }
}