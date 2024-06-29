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
import com.example.coachreport.databinding.ActivityPerbaruiMateriBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerbaruiMateriActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPerbaruiMateriBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerbaruiMateriBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val judul_materi = this.intent.getStringExtra("judul")
        val deskripsi = this.intent.getStringExtra("deskripsi")

        binding.edtJudulMateri.setText(judul_materi.toString())
        binding.edtDeskripsi.setText(deskripsi.toString())

        binding.btnPerbaruidata.setOnClickListener {
            val judul = binding.edtJudulMateri.text.toString().trim()
            val deskripsi = binding.edtDeskripsi.text.toString().trim()
            updatemateri(judul, deskripsi)

        }

    }

    private fun updatemateri (judul : String, deskripsi : String) {
        val id = this.intent.getStringExtra("id")
        if (id != null) {
            APIConfig.getService(this).updateMateri(id, judul, deskripsi).enqueue(object : Callback<MateriResponse> {
                override fun onResponse(
                    call: Call<MateriResponse>,
                    response: Response<MateriResponse>
                ) {
                    if (response.code() == 200) {
                       Toast.makeText(this@PerbaruiMateriActivity, "Update Data Berhasil", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@PerbaruiMateriActivity, KelolaMateriActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        startActivity(intent)
                        finish()
                    } else (
                            Toast.makeText(this@PerbaruiMateriActivity, "Gagal mengupdate data", Toast.LENGTH_LONG).show()
                    )
                }

                override fun onFailure(call: Call<MateriResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}