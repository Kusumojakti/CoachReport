package com.example.coachreport.api.response.absensiResponse

import com.google.gson.annotations.SerializedName

data class SiswaItem(

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

	@field:SerializedName("noIdentitas")
	val noIdentitas: String? = null,

	@field:SerializedName("jadwal_kelas_id")
	val jadwalKelasId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("mulai")
	val mulai: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("noTelp")
	val noTelp: String? = null
)

data class Data(

	@field:SerializedName("ulasan")
	val ulasan: Ulasan? = null,

	@field:SerializedName("siswa")
	val siswa: List<SiswaItem?>? = null,

	@field:SerializedName("absensi")
	val absensi: List<AbsensiItem?>? = null
)

data class Ulasan(

	@field:SerializedName("pertemuans_id")
	val pertemuansId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("jadwal_kelas_id")
	val jadwalKelasId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("pertemuan_ke")
	val pertemuanKe: Int? = null
)

data class AbsensiItem(

	@field:SerializedName("pertemuans_id")
	val pertemuansId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Any? = null,

	@field:SerializedName("created_at")
	val createdAt: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("siswas_id")
	val siswasId: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class AbsensiGetResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
