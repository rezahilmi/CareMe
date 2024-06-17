package com.example.storyapp.data.network

import com.example.careme.data.network.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
//    @FormUrlEncoded
//    @POST("register")
//    suspend fun register(
//        @Field("name") name: String,
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
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
//    @POST("stories")
//    suspend fun uploadImage(
//        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody,
//        @Part("lat") lat: RequestBody?,
//        @Part("lon") long: RequestBody?
//    ): UploadStoryResponse
}