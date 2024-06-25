package com.example.coachreport.api.response.siswaResponse

import com.google.gson.annotations.SerializedName

data class SiswaDeleteResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
