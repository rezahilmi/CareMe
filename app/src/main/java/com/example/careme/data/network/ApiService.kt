package com.example.careme.data.network

import com.example.careme.data.network.dataModel.LoginRequest
import com.example.careme.data.network.dataModel.RegisterRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

//    @GET("stories")
//    suspend fun getStories(
//        @Query("page") page: Int,
//        @Query("size") size: Int
//    ): StoryResponse
//    @GET("stories")
//    suspend fun getStoriesWithLocation(
//        @Query("location") location : Int = 1,
//    ): StoryResponse
//
//    @GET("stories/{id}")
//    suspend fun getDetailStory(@Path("id") id: String): DetailStoryResponse
//
//    @Multipart
//    @POST("predict")
//    suspend fun predict(
//    @Part file: MultipartBody.Part
//    ): PredictionResponse
}