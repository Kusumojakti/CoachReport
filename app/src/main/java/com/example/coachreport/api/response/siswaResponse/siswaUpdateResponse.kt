package com.example.coachreport.api.response.siswaResponse

import com.google.gson.annotations.SerializedName

data class DataUpdate(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("noIdentitas")
	val noIdentitas: Long? = null,

	@field:SerializedName("jadwal_kelas_id")
	val jadwalKelasId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("noTelp")
	val noTelp: String? = null
)

data class SiswaUpdateResponse(

	@field:SerializedName("data")
	val data: DataUpdate? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
