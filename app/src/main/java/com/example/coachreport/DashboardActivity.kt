package com.example.coachreport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachreport.absensi.KelolaAbsensiActivity
import com.example.coachreport.adapter.adapterKelasMendatang
import com.example.coachreport.api.APIConfig
import com.example.coachreport.api.response.MateriResponse
import com.example.coachreport.api.response.kelasResponse.ClassItem
import com.example.coachreport.api.response.kelasResponse.DataItem
import com.example.coachreport.api.response.kelasResponse.KelasIndexResponse
import com.example.coachreport.api.response.kelasResponse.KelasTodayResponse
import com.example.coachreport.api.response.siswaResponse.SiswaIndexResponse
import com.example.coachreport.auth.LoginActivity
import com.example.coachreport.databinding.ActivityDashboardBinding
import com.example.coachreport.jadwal.DetailJadwalKelas
import com.example.coachreport.jadwal.KelolaJadwalActivity
import com.example.coachreport.materi.KelolaMateriActivity
import com.example.coachreport.profile.ProfileActivity
import com.example.coachreport.siswa.KelolaDataSiswaActivity
import com.example.coachreport.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var adapterKelasMendatang: adapterKelasMendatang

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = SessionManager
        val username = sharedPreferences.getName(this)
        binding.txtusername.text = username
        binding.cardJadwal.visibility = View.GONE


        binding.menumateri.setOnClickListener {
            val intent = Intent(this, KelolaMateriActivity::class.java)
            startActivity(intent)
        }

        binding.menusiswa.setOnClickListener{
            startActivity(Intent(this, KelolaDataSiswaActivity::class.java))
        }

        binding.circleImageView.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.menujadwal.setOnClickListener {
            val intent = Intent(this, KelolaJadwalActivity::class.java)
            startActivity(intent)
        }

        binding.menuabsensi.setOnClickListener {
            val intent = Intent(this, KelolaAbsensiActivity::class.java)
            startActivity(intent)
        }

        adapterKelasMendatang = adapterKelasMendatang(this, ArrayList()) // Initialize adapterKelasMendatang
        binding.rvOncomingclass.layoutManager = LinearLayoutManager(this)
        binding.rvOncomingclass.adapter = adapterKelasMendatang

    }

    override fun onResume() {
        super.onResume()
        getData()
    }

//    private fun getOnComingClass(jadwallist: List<DataItem?>): List<DataItem?> {
//        val today = LocalDate.now().dayOfWeek.name
//        val now = LocalTime.now()
//
//        return jadwallist.filter {
//            it?.hari.equals(today, ignoreCase = true) && LocalTime.parse(it?.mulai ?: "", DateTimeFormatter.ISO_TIME).isAfter(now)
//        }
//    }
    private fun getKelasSedangBerlangsung(jadwallist: List<ClassItem?>): List<ClassItem?> {
        val today = LocalDate.now().dayOfWeek.name
        val now = LocalTime.now()

        return jadwallist.filter {
            it?.hari.equals(today, ignoreCase = true) &&
                    LocalTime.parse(it?.mulai ?: "", DateTimeFormatter.ISO_TIME).isBefore(now) &&
                    LocalTime.parse(it?.selesai ?: "", DateTimeFormatter.ISO_TIME).isAfter(now)
        }
    }

    private fun getData() {
//        get jadwalHari ini
        APIConfig.getService(this).kelashariini().enqueue(object : Callback<KelasTodayResponse> {
            override fun onResponse(
                call: Call<KelasTodayResponse>,
                response: Response<KelasTodayResponse>
            ) {
                if (response.code() == 200) {
                    Log.d("DATA", response.body().toString())
                    if (response.body() != null) {
                        val responsejadwal = response.body()
                        val datajadwal = responsejadwal?.data

                        if (datajadwal != null) {
                            adapterKelasMendatang.updateData(datajadwal)

                            // Update data untuk kelas sedang berlangsung
                            val ongoingClasses = getKelasSedangBerlangsung(datajadwal)
                            displayOngoingClasses(ongoingClasses)
                        }
                    }
                } else {
                    Toast.makeText(this@DashboardActivity, response.message(), Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: Call<KelasTodayResponse>, t: Throwable) {
                Log.d("DISINIII", t.message.toString())
                Toast.makeText(this@DashboardActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
//        get data siswa
        APIConfig.getService(this).indexSiswa().enqueue(object : Callback<SiswaIndexResponse>{
            override fun onResponse(
                call: Call<SiswaIndexResponse>,
                response: Response<SiswaIndexResponse>
            ) {
                if (response.code() == 200) {
                    binding.txtTotalsiswa.text = response?.body()?.data?.size.toString()
                }
            }

            override fun onFailure(call: Call<SiswaIndexResponse>, t: Throwable) {

            }
        })

//        get data kelas
        APIConfig.getService(this).indexKelas().enqueue(object : Callback<KelasIndexResponse>{
            override fun onResponse(
                call: Call<KelasIndexResponse>,
                response: Response<KelasIndexResponse>
            ) {
                if (response.code() == 200) {
                    binding.txtTotalkelas.text = response.body()?.data?.size.toString()
                }
            }

            override fun onFailure(call: Call<KelasIndexResponse>, t: Throwable) {

            }
        })
//        get data materi
        APIConfig.getService(this).getMateri().enqueue(object : Callback<MateriResponse>{
            override fun onResponse(
                call: Call<MateriResponse>,
                response: Response<MateriResponse>
            ) {
                if (response.code() == 200) {
                    binding.txtTotalmateri.text = response.body()?.data?.size.toString()
                }
            }

            override fun onFailure(call: Call<MateriResponse>, t: Throwable) {

            }
        })
    }

    private fun displayOngoingClasses(ongoingClasses: List<ClassItem?>) {
        Log.d("langsung ges", ongoingClasses.toString())
        if (!ongoingClasses.isNullOrEmpty()) {
            binding.cardJadwal.visibility = View.VISIBLE
            val judul = ongoingClasses.get(0)?.materis?.judul
            val kelas = ongoingClasses.get(0)?.nama
            val hari = ongoingClasses.get(0)?.hari
           val mulai = ongoingClasses.get(0)?.mulai
           val selesai = ongoingClasses.get(0)?.selesai
            val tempat = ongoingClasses.get(0)?.tempat

            val includeView: View = binding.sedangBerlangsung.root
            val txtNamaMateri: TextView = includeView.findViewById(R.id.txt_namamaterijadwal)
            val txtnamakelas: TextView = includeView.findViewById(R.id.txt_namakelas)
            val txthari : TextView = includeView.findViewById(R.id.txt_namahari)
            val txtmulai: TextView = includeView.findViewById(R.id.txt_waktumulai)
            val txtselesai: TextView = includeView.findViewById(R.id.txt_waktuselesai)
            binding.cardJadwal.setOnClickListener {
                    val intent = Intent(this, DetailJadwalKelas::class.java)
                    intent.putExtra("id", ongoingClasses.get(0)?.id)
                    intent.putExtra("nama", kelas)
                    intent.putExtra("judul", judul)
                    intent.putExtra("hari", hari)
                    intent.putExtra("tempat", tempat)
                    intent.putExtra("mulai", mulai)
                    intent.putExtra("selesai", selesai)
                    startActivity(intent)
            }

            if (!ongoingClasses.isNullOrEmpty()){
                txtNamaMateri.text = judul
                txtnamakelas.text = kelas
                txthari.text = hari
                txtmulai.text = mulai
                txtselesai.text = selesai
            }
        }

    }
}
