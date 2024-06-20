package com.example.careme.data.network

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("result")
	val result: LoginResult? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class LoginResult(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
