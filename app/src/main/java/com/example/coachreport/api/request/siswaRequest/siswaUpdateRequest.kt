package com.example.coachreport.api.request.siswaRequest

import com.google.gson.annotations.SerializedName

data class SiswaUpdateRequest(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("jadwal_kelas_id")
	val jadwalKelasId: Int? = null,

	@field:SerializedName("noTelp")
	val noTelp: String? = null
)
