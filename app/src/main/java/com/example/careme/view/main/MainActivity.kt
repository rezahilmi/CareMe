package com.example.careme.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.careme.databinding.ActivityMainBinding
import com.example.careme.view.ViewModelFactory
import com.example.careme.view.authentication.AuthenticationActivity
import com.example.careme.view.history.HistoryActivity
import com.example.careme.view.prediction.PredictionActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, AuthenticationActivity::class.java))
                finish()
            }
        }

        binding.scanButton.setOnClickListener {
            startActivity(Intent(this, PredictionActivity::class.java))
        }
        binding.historyButton.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }
}