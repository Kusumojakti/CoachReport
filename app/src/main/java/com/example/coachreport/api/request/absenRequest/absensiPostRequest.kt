package com.example.coachreport.api.request.absenRequest

import com.google.gson.annotations.SerializedName

data class AbsensiPostRequest(

	@field:SerializedName("ulasan")
	val ulasan: String? = null,

	@field:SerializedName("siswa")
	val siswa: List<SiswaItem?>? = null,

	@field:SerializedName("jadwal_kelas_id")
	val jadwalKelasId: Int? = null,

	@field:SerializedName("pertemuan_ke")
	val pertemuanKe: Int? = null
)

data class SiswaItem(

	@field:SerializedName("siswas_id")
	val siswasId: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
