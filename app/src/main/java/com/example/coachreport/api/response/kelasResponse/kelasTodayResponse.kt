package com.example.coachreport.api.response.kelasResponse

import com.google.gson.annotations.SerializedName

data class DataItems(

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

    @field:SerializedName("materis")
    val materis: Material? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("mulai")
    val mulai: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)

data class Material(

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null,

    @field:SerializedName("judul")
    val judul: String? = null
)

data class KelasTodayResponse(

    @field:SerializedName("data")
    val data: List<DataItems?>? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)
