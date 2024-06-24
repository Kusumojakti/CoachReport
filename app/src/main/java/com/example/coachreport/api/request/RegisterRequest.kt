package com.example.coachreport.api.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("password_confirmation")
	val passwordConfirmation: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("noHp")
	val noHp: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
