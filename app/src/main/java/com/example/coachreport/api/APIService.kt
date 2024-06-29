package com.example.coachreport.api

import com.example.coachreport.api.request.absenRequest.AbsensiGetRequest
import com.example.coachreport.api.request.absenRequest.AbsensiPostRequest
import com.example.coachreport.api.request.jadwalRequest.KelasStoreRequest
import com.example.coachreport.api.request.jadwalRequest.KelasUpdateRequest
import com.example.coachreport.api.request.siswaRequest.SiswaStoreRequest
import com.example.coachreport.api.request.siswaRequest.SiswaUpdateRequest
import com.example.coachreport.api.response.LoginResponse
import com.example.coachreport.api.response.MateriResponse
import com.example.coachreport.api.response.RegisterResponse
import com.example.coachreport.api.response.absensiResponse.AbsensiGetResponse
import com.example.coachreport.api.response.absensiResponse.AbsensiPostResponse
import com.example.coachreport.api.response.kelasResponse.KelasIndexResponse
import com.example.coachreport.api.response.kelasResponse.KelasTodayResponse
import com.example.coachreport.api.response.kelasResponse.KelasUpdateResponse
import com.example.coachreport.api.response.siswaResponse.SiswaDeleteResponse
import com.example.coachreport.api.response.siswaResponse.SiswaIndexResponse
import com.example.coachreport.api.response.siswaResponse.SiswaStoreResponse
import com.example.coachreport.api.response.siswaResponse.SiswaUpdateResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @FormUrlEncoded
    @POST("api/login")
    fun AuthLogin(
        @Field("noHp") phone: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("api/register")
    fun AuthRegist(
        @Field("name") nama: String,
        @Field("noHp") noHp: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
    ) : Call<RegisterResponse>

//  endpoint  materi
    @FormUrlEncoded
    @POST("api/materi")
    fun addMateri(
        @Field("judul") judul: String,
        @Field("deskripsi") deskripsi: String
    ): Call<MateriResponse>

    @GET("api/materi")
    fun getMateri() : Call<MateriResponse>

    @DELETE("api/materi/{id}")
    fun deleteMateri(@Path("id") id: String): Call<MateriResponse>

    @FormUrlEncoded
    @PUT("api/materi/{id}")
    fun updateMateri(
        @Path("id") id: String,

        @Field("judul") judul: String,
        @Field("deskripsi") deskripsi : String
    ) : Call<MateriResponse>

    //    endpoint jadwal
    @GET("api/jadwal")
    fun indexKelas() : Call<KelasIndexResponse>

    @GET("api/getjadwaltoday")
    fun kelashariini() : Call<KelasTodayResponse>

    @POST("api/jadwal")
    fun addJadwalKelas(@Body dataJadwal : KelasStoreRequest) : Call<KelasStoreRequest>

    @PUT("api/jadwal/{id}")
    fun updatejadwal(@Path("id") id: String, @Body dataJadwal: KelasUpdateRequest) : Call<KelasUpdateResponse>

    @DELETE("api/jadwal/{id}")
    fun deleteJadwal(@Path("id") id: String) : Call<KelasIndexResponse>

    //    endpoint Siswa
    @GET("api/siswa")
    fun indexSiswa() : Call<SiswaIndexResponse>

    @POST("api/siswa")
    fun storeSiswa(@Body dataSiswa: SiswaStoreRequest) : Call<SiswaStoreResponse>

    @PUT("api/siswa/{id}")
    fun updateSiswa(@Path("id") id: String, @Body dataSiswa: SiswaUpdateRequest) : Call<SiswaUpdateResponse>

    @DELETE("api/siswa/{id}")
    fun deleteSiswa(@Path("id") id: String) : Call<SiswaDeleteResponse>

    // endpoint absen
    @GET("api/getabsen")
    fun getabsensi(
        @Query("pertemuan_ke") pertemuanKe: Int?,
        @Query("jadwal_kelas_id") jadwalKelasId: Int?
    ): Call<AbsensiGetResponse>

    @POST("api/absensi")
    fun saveabsensi(@Body simpanabsensi : AbsensiPostRequest) : Call<AbsensiPostResponse>

    @DELETE("api/deldataabsensi")
    fun deleteabsensi(
        @Query("pertemuan_ke") pertemuanKe: Int?,
        @Query("jadwal_kelas_id") jadwalKelasId: Int?
    ): Call<AbsensiGetResponse>

}

