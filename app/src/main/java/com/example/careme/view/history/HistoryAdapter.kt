package com.example.careme.view.history

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.careme.data.network.ResultItem
import com.example.careme.databinding.ItemHistoryBinding

@Suppress("DEPRECATION")
class HistoryAdapter :
    PagingDataAdapter<ResultItem, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = getItem(position)
        if (history != null) {
            holder.bind(history)
        }
    }

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: ResultItem) {
            binding.tvCardTitle.text = history.predictionResult
            Glide.with(itemView.context).load(history.imageUrl).into(binding.ivCardImage)

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val historyId = getItem(position)
                    val intent = Intent(itemView.context, SpecificHistoryActivity::class.java).apply {
                        putExtra(SpecificHistoryActivity.KEY_ID, historyId?.id)
                    }

                    val imagePair = androidx.core.util.Pair(binding.ivCardImage as View, "image")
                    val titlePair = androidx.core.util.Pair(binding.tvCardTitle as View, "title")

                    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        imagePair,
                        titlePair
                    )

                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultItem>() {
            override fun areItemsTheSame(oldItem: ResultItem, newItem: ResultItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ResultItem, newItem: ResultItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}

