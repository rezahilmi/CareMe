package com.example.careme.view.result

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.careme.R
import com.example.careme.databinding.ActivityResultBinding
import com.example.careme.view.main.MainActivity
import com.example.careme.view.prediction.PredictionActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val predictionResult = intent.getStringExtra("predictionResult")
        val imageUrl = intent.getStringExtra("imageUrl")
        val description = intent.getStringExtra("description")
        val recommendation = intent.getStringExtra("recommendation")

        Glide.with(this)
            .load(imageUrl)
            .into(binding.resultImage)
        binding.resultDiseaseText.text = predictionResult
        binding.resultDescriptionText.text = description
//        binding.recommendationTextView.text = recommendation

        binding.analyzeAgainButton.setOnClickListener {
            startActivity(Intent(this, PredictionActivity::class.java))
        }
    }
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }
}