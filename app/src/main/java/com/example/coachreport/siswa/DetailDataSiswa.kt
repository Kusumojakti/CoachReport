package com.example.coachreport.siswa

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
//        WindowCompat.setDecorFitsSystemWindows(window, false)
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
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_delete)
            dialog.setCancelable(true)

            val btnYa = dialog.findViewById<Button>(R.id.btn_okay)
            val btnTidak = dialog.findViewById<Button>(R.id.btn_cancel)

            btnYa.setOnClickListener {
                deleteSiswa(noIdentitas.toString())
                dialog.dismiss()
            }

            btnTidak.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
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
                    val intent = Intent(this@DetailDataSiswa, KelolaDataSiswaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)

                }
                Toast.makeText(this@DetailDataSiswa, response.message(), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<SiswaDeleteResponse>, t: Throwable) {
                Toast.makeText(this@DetailDataSiswa,  t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}