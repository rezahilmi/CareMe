package com.example.careme.view.scanImage

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.careme.R
import com.example.careme.databinding.ActivityMainBinding
import com.example.careme.databinding.ActivityScanImageBinding
import com.example.careme.view.result.ResultActivity

class ScanImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        binding.analyzeButton.setOnClickListener {
            startActivity(Intent(this, ResultActivity::class.java))
        }
    }
}