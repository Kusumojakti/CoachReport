package com.example.coachreport.api.response.siswaResponse

import com.google.gson.annotations.SerializedName

data class DataItem(

	@field:SerializedName("jadwal_kelas")
	val jadwalKelas: JadwalKelas? = null,

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

data class JadwalKelas(

	@field:SerializedName("hari")
	val hari: String? = null,

	@field:SerializedName("tempat")
	val tempat: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("materis_id")
	val materisId: Int? = null,

	@field:SerializedName("selesai")
	val selesai: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("mulai")
	val mulai: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class SiswaIndexResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
