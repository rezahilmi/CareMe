package com.example.careme.di

import android.content.Context
import com.example.careme.data.UserRepository
import com.example.careme.data.network.ApiConfig
import com.example.careme.data.pref.UserPreference
import com.example.careme.data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object  Injection {
    fun provideRepository(context: Context): UserRepository = runBlocking {
//        val database = StoryDatabase.getDatabase(context)
        val pref = UserPreference.getInstance(context.dataStore)
        val user = pref.getSession().first()
        val apiService = ApiConfig.getApiService(user.token)
        UserRepository.getInstance(apiService, pref)
    }
}