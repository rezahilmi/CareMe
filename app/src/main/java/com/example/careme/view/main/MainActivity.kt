package com.example.careme.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.careme.R
import com.example.careme.databinding.ActivityAuthenticationBinding
import com.example.careme.databinding.ActivityMainBinding
import com.example.careme.view.authentication.LoginBottomSheetFragment
import com.example.careme.view.history.HistoryActivity
import com.example.careme.view.result.ResultActivity
import com.example.careme.view.scanImage.ScanImageActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        binding.scanButton.setOnClickListener {
            startActivity(Intent(this, ScanImageActivity::class.java))
        }
        binding.historyButton.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }
}