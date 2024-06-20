package com.example.careme.view.history

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.careme.R
import com.example.careme.data.network.SpecificHistoryResult
import com.example.careme.databinding.ActivitySpecificHistoryBinding
import com.example.careme.view.ViewModelFactory
import com.example.careme.view.recommendation.RecommendationActivity

class SpecificHistoryActivity : AppCompatActivity() {

    companion object {
        const val KEY_ID = "story_id"
    }
    private lateinit var binding: ActivitySpecificHistoryBinding
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySpecificHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val factory = ViewModelFactory.getInstance(this)
        historyViewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]

        val historyId = intent.getStringExtra(KEY_ID)
        if (historyId != null) {
            historyViewModel.fetchDetailHistory(historyId)
            historyViewModel.historyDetail.observe(this) { response ->
                response?.let { specificHistoryResponse ->
                    specificHistoryResponse.result?.let { result ->
                        setSpecificHistoryData(result)
                    }
                }
            }
        }
        historyViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
        binding.recommendationButton.setOnClickListener{
            val recommendation = binding.recommendationButton.tag as? ArrayList<String>
            val intent = Intent(this, RecommendationActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                putStringArrayListExtra("recommendation", recommendation)
            }
            startActivity(intent)
        }
    }
    private fun setSpecificHistoryData(history: SpecificHistoryResult) {
        binding.rhDiseaseText.text = history.predictionResult
        binding.rhDescriptionText.text = history.description
        Glide.with(this).load(history.imageUrl).into(binding.resultHistoryImage)

        binding.recommendationButton.tag = history.recommendation
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.dimmingView.visibility = View.VISIBLE

            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            binding.progressBar.visibility = View.GONE
            binding.dimmingView.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }
}