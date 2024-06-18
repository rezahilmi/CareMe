package com.example.careme.data.network

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
