package com.example.coachreport.api.request.absenRequest

import com.google.gson.annotations.SerializedName

data class AbsensiGetRequest(

	@field:SerializedName("jadwal_kelas_id")
	val jadwalKelasId: Int? = null,

	@field:SerializedName("pertemuan_ke")
	val pertemuanKe: Int? = null
)
