package com.example.coachreport.api.request.siswaRequest

import com.google.gson.annotations.SerializedName

data class SiswaStoreRequest(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("noIdentitas")
	val noIdentitas: String? = null,

	@field:SerializedName("jadwal_kelas_id")
	val jadwalKelasId: Int? = null,

	@field:SerializedName("noTelp")
	val noTelp: String? = null
)
