package com.example.coachreport.siswa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coachreport.R
import com.example.coachreport.api.APIConfig
import com.example.coachreport.api.response.siswaResponse.SiswaDeleteResponse
import com.example.coachreport.databinding.ActivityDetailDataSiswaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailDataSiswa : AppCompatActivity() {
    private lateinit var binding: ActivityDetailDataSiswaBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityDetailDataSiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nama = intent.getStringExtra("nama")
        val kelas = intent.getStringExtra("kelas")
        val noIdentitas = intent.getStringExtra("noIdentitas")
        val noTelp = intent.getStringExtra("noTelp")

        binding.textNama.text = nama
        binding.noIdentitas.text = noIdentitas
        binding.noTelpon.text = noTelp
        binding.namaKelas.text = kelas

        binding.btnHapusSiswa.setOnClickListener {
            deleteSiswa(noIdentitas.toString())
        }

        binding.btnPerbaruiSiswa.setOnClickListener {
            val intent = Intent(this, PerbaruiDataSiswaActivity::class.java)
            intent.putExtra("nama", nama)
            intent.putExtra("kelas", kelas)
            intent.putExtra("noIdentitas", noIdentitas)
            intent.putExtra("noTelp", noTelp)
            startActivity(intent)
        }
    }

    private fun deleteSiswa(noIdentitas: String) {
        APIConfig.getService(this).deleteSiswa(noIdentitas).enqueue(object : Callback<SiswaDeleteResponse> {
            override fun onResponse(
                call: Call<SiswaDeleteResponse>,
                response: Response<SiswaDeleteResponse>
            ) {
                if (response.code() == 200) {
                    Toast.makeText(this@DetailDataSiswa, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@DetailDataSiswa, KelolaDataSiswaActivity::class.java))
                }
                Toast.makeText(this@DetailDataSiswa, response.message(), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<SiswaDeleteResponse>, t: Throwable) {
                Toast.makeText(this@DetailDataSiswa,  t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}