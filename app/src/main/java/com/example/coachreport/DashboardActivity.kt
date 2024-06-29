package com.example.coachreport

import android.content.Intent
import android.os.Bundle
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
import com.example.coachreport.api.response.kelasResponse.DataItem
import com.example.coachreport.api.response.kelasResponse.DataItems
import com.example.coachreport.api.response.kelasResponse.KelasIndexResponse
import com.example.coachreport.api.response.kelasResponse.KelasTodayResponse
import com.example.coachreport.databinding.ActivityDashboardBinding
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

        val jumlahSiswa = intent.getIntExtra("jumlah_siswa", 0)
        binding.txtTotalsiswa.text = "$jumlahSiswa"

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

    private fun getKelasSedangBerlangsung(jadwallist: List<DataItems?>): List<DataItems?> {
        val today = LocalDate.now().dayOfWeek.name
        val now = LocalTime.now()

        return jadwallist.filter {
            it?.hari.equals(today, ignoreCase = true) &&
                    LocalTime.parse(it?.mulai ?: "", DateTimeFormatter.ISO_TIME).isBefore(now) &&
                    LocalTime.parse(it?.selesai ?: "", DateTimeFormatter.ISO_TIME).isAfter(now)
        }
    }

    private fun getData() {
        APIConfig.getService(this).kelashariini().enqueue(object : Callback<KelasTodayResponse> {
            override fun onResponse(
                call: Call<KelasTodayResponse>,
                response: Response<KelasTodayResponse>
            ) {
                if (response.code() == 200) {
                    val responsejadwal = response.body()
                    val datajadwal = responsejadwal?.data

                    if (datajadwal != null) {
                        adapterKelasMendatang.updateData(datajadwal)

                        // Update data untuk kelas sedang berlangsung
                        val ongoingClasses = getKelasSedangBerlangsung(datajadwal)
                        displayOngoingClasses(ongoingClasses)
                    } else {
                        binding.datanotfound.visibility = View.VISIBLE
                        binding.rvOncomingclass.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(this@DashboardActivity, "Failed to get data", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<KelasTodayResponse>, t: Throwable) {
                Toast.makeText(this@DashboardActivity, "Data Not Found", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun displayOngoingClasses(ongoingClasses: List<DataItems?>) {
        val container = findViewById<LinearLayout>(R.id.card_jadwal)
        container.removeAllViews()

        for (kelas in ongoingClasses) {
            val view: View = LayoutInflater.from(this).inflate(R.layout.fetch_jadwal, container, false)
            val txtNamaMateri: TextView = view.findViewById(R.id.txt_namamaterijadwal)
            val txtnamakelas: TextView = view.findViewById(R.id.txt_namakelas)
            val txthari : TextView = view.findViewById(R.id.txt_namahari)
            val txtmulai: TextView = view.findViewById(R.id.txt_waktumulai)
            val txtselesai: TextView = view.findViewById(R.id.txt_waktuselesai)

            txtNamaMateri.text = (kelas?.materis?.judul ?: "Unknown").toString()
            txtnamakelas.text = kelas?.nama
            txthari.text = kelas?.hari
            txtmulai.text = kelas?.mulai
            txtselesai.text = kelas?.selesai



            container.addView(view)
        }
    }
}
