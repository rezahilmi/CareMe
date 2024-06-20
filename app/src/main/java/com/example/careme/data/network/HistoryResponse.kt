package com.example.careme.data.network

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("result")
	val result: List<ResultItem>  = emptyList(),

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

@Entity(tableName = "history")
data class ResultItem(

	@PrimaryKey
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("predictionResult")
	val predictionResult: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null

)
