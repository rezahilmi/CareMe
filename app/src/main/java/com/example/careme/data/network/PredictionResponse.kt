package com.example.careme.data.network

import com.google.gson.annotations.SerializedName

data class PredictionResponse(

	@field:SerializedName("result")
	val result: PredictResult? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class PredictResult(

	@field:SerializedName("predictionResult")
	val predictionResult: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("recommendation")
	val recommendation: List<String?>? = null,

	@field:SerializedName("id")
	val id: String? = null
)
