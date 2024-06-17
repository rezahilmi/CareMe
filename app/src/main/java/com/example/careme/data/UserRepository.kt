package com.example.careme.data

import com.example.careme.data.pref.UserModel
import com.example.careme.data.pref.UserPreference
import com.example.storyapp.data.network.ApiService
import com.example.careme.data.network.LoginResponse
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

//    suspend fun registerUser(name: String, email: String, password: String): RegisterResponse {
//        return apiService.register(name, email, password)
//    }

    suspend fun loginUser(email: String, password: String): LoginResponse {
        val loginResponse = apiService.login(email, password)
        if (loginResponse.error == false) {
            userPreference.saveSession(
                UserModel(
                    loginResponse.loginResult?.userId ?: "",
                    loginResponse.loginResult?.name ?: "",
                    loginResponse.loginResult?.token ?: ""
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

//    fun getStories(): LiveData<PagingData<ListStoryItem>> {
//        @OptIn(ExperimentalPagingApi::class)
//        return Pager(
//            config = PagingConfig(
//                pageSize = 20
//            ),
//            remoteMediator = StoryRemoteMediator(storyDatabase, apiService),
//            pagingSourceFactory = {
////                StoryPagingSource(apiService)
//                storyDatabase.storyDao().getAllStory()
//            }
//        ).liveData
//    }

//    suspend fun getStoriesWithLocation(): StoryResponse {
//        return apiService.getStoriesWithLocation()
//    }
//
//    suspend fun getStoryDetail(id: String): DetailStoryResponse {
//        return apiService.getDetailStory(id)
//    }
//
//    suspend fun uploadImage(imageFile: MultipartBody.Part, description: RequestBody,lat: RequestBody?, lon: RequestBody?): UploadStoryResponse {
//        return apiService.uploadImage(imageFile, description, lat, lon)
//    }

    companion object {
        fun getInstance(apiService: ApiService,
                        userPreference: UserPreference) = UserRepository(apiService, userPreference)
    }
}
