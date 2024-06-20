package com.example.careme.data

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.careme.data.pref.UserModel
import com.example.careme.data.pref.UserPreference
import com.example.careme.data.network.ApiService
import com.example.careme.data.network.LoginResponse
import com.example.careme.data.network.PredictionResponse
import com.example.careme.data.network.dataModel.RegisterRequest
import com.example.careme.data.network.RegisterResponse
import com.example.careme.data.network.ResultItem
import com.example.careme.data.network.SpecificHistoryResponse
import com.example.careme.data.network.dataModel.LoginRequest
import com.example.careme.database.HistoryDatabase
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
    private val historyDatabase: HistoryDatabase

) {

    suspend fun registerUser(registerRequest: RegisterRequest): RegisterResponse {
        return apiService.register(registerRequest)
    }

    suspend fun loginUser(loginRequest: LoginRequest): LoginResponse {
        val loginResponse = apiService.login(loginRequest)
        if (loginResponse.status == "success") {
            userPreference.saveSession(
                UserModel(
                    loginResponse.result?.id ?: "",
                    loginResponse.result?.name ?: "",
                    loginResponse.result?.token ?: ""
                )
            )
        }
        return loginResponse
    }


    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun getHistory(): LiveData<PagingData<ResultItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = HistoryRemoteMediator(historyDatabase, apiService),
            pagingSourceFactory = {
//                HistoryPagingSource(apiService)
                historyDatabase.historyDao().getAllHistory()
            }
        ).liveData
    }

//    suspend fun getStoriesWithLocation(): StoryResponse {
//        return apiService.getStoriesWithLocation()
//    }
//
    suspend fun getSpecificHistory(id: String): SpecificHistoryResponse {
        return apiService.getSpecificHistory(id)
    }

    suspend fun predict(imageFile: MultipartBody.Part): PredictionResponse {
        return apiService.predict(imageFile)
    }

    companion object {
        fun getInstance(apiService: ApiService,
                        userPreference: UserPreference, historyDatabase: HistoryDatabase) = UserRepository(apiService, userPreference, historyDatabase)
    }
}
