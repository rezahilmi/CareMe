package com.example.careme.data.network

import com.google.gson.annotations.SerializedName

data class SpecificHistoryResponse(

	@field:SerializedName("result")
	val result: SpecificHistoryResult? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class SpecificHistoryResult(

	@field:SerializedName("predictionResult")
	val predictionResult: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("recommendation")
	val recommendation: List<String?>? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)
