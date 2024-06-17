package com.example.careme.data.network

import com.google.gson.annotations.SerializedName

data class LoginGoogleResponse(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("data")
	val data: Data? = null
)

data class Data(

	@field:SerializedName("at_hash")
	val atHash: String? = null,

	@field:SerializedName("sub")
	val sub: String? = null,

	@field:SerializedName("email_verified")
	val emailVerified: Boolean? = null,

	@field:SerializedName("iss")
	val iss: String? = null,

	@field:SerializedName("given_name")
	val givenName: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null,

	@field:SerializedName("aud")
	val aud: String? = null,

	@field:SerializedName("azp")
	val azp: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("exp")
	val exp: Int? = null,

	@field:SerializedName("family_name")
	val familyName: String? = null,

	@field:SerializedName("iat")
	val iat: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)
