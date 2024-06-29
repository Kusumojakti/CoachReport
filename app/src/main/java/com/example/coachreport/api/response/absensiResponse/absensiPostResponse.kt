package com.example.coachreport.api.response.absensiResponse

import com.google.gson.annotations.SerializedName

data class AbsensiPostResponse(

	@field:SerializedName("data")
	val data: reqabsen? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class reqabsen(

	@field:SerializedName("Pertemuan_ke")
	val pertemuanKe: Int? = null,

	@field:SerializedName("Absensi")
	val absensi: List<AbsensiItems?>? = null,

	@field:SerializedName("Ulasan")
	val ulasan: String? = null
)

data class AbsensiItems(

	@field:SerializedName("pertemuans_id")
	val pertemuansId: Int? = null,

	@field:SerializedName("siswas_id")
	val siswasId: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
