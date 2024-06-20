package com.example.careme.view.history

import android.os.Bundle
import android.view.View
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

class SpecificHistoryActivity : AppCompatActivity() {

    companion object {
        const val KEY_ID = "story_id"
    }
    private lateinit var binding: ActivitySpecificHistoryBinding
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_specific_history)
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
    }
    private fun setSpecificHistoryData(history: SpecificHistoryResult) {
        binding.titleTextView.text = history.predictionResult
        Glide.with(this).load(history.imageUrl).into(binding.resultHistoryImage)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}