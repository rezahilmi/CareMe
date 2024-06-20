package com.example.careme.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.careme.data.UserRepository
import com.example.careme.di.Injection
import com.example.careme.view.authentication.AuthenticationViewModel
import com.example.careme.view.history.HistoryViewModel
import com.example.careme.view.main.MainViewModel
import com.example.careme.view.prediction.PredictionViewModel

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthenticationViewModel::class.java) -> {
                AuthenticationViewModel(repository) as T
            }
            modelClass.isAssignableFrom(PredictionViewModel::class.java) -> {
                PredictionViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }

    }

    companion object {
        @JvmStatic
        fun getInstance(context: Context) = ViewModelFactory(Injection.provideRepository(context))
    }
}