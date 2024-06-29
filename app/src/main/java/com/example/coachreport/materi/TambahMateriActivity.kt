package com.example.coachreport.materi

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coachreport.R
import com.example.coachreport.api.APIConfig
import com.example.coachreport.api.response.MateriResponse
import com.example.coachreport.databinding.ActivityTambahMateriBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahMateriActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTambahMateriBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahMateriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSimpanMateri.setOnClickListener {
            val judul = binding.edtJudulMateri.text.toString().trim()
            val deskripsi = binding.edtDeskripsi.text.toString().trim()
            addMateri(judul, deskripsi)
        }

    }

    private fun addMateri(judul : String, deskripsi: String) {
        APIConfig.getService(this).addMateri(judul, deskripsi).enqueue(object : Callback<MateriResponse> {
            override fun onResponse(
                call: Call<MateriResponse>,
                response: Response<MateriResponse>
            ) {
                if (response.code() == 200) {
                    Toast.makeText(this@TambahMateriActivity, "Sukses menambahkan materi", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@TambahMateriActivity, KelolaMateriActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {
                    Toast.makeText(this@TambahMateriActivity, "Gagal menambahkan data", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<MateriResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}