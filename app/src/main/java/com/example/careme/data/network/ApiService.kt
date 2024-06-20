package com.example.careme.data.network

import com.example.careme.data.network.dataModel.LoginRequest
import com.example.careme.data.network.dataModel.RegisterRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("history")
    suspend fun getHistory(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): HistoryResponse

    @GET("history/{id}")
    suspend fun getSpecificHistory(@Path("id") id: String): SpecificHistoryResponse

    @Multipart
    @POST("predict")
    suspend fun predict(
    @Part file: MultipartBody.Part
    ): PredictionResponse
}