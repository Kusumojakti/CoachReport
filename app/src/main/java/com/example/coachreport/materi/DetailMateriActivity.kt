package com.example.coachreport.materi

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coachreport.R
import com.example.coachreport.api.APIConfig
import com.example.coachreport.api.APIService
import com.example.coachreport.api.response.MateriResponse
import com.example.coachreport.databinding.ActivityDetailMateriBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class DetailMateriActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailMateriBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMateriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = this.intent.getStringExtra("id")
        val judul_materi = this.intent.getStringExtra("judul")
        val deskripsi = this.intent.getStringExtra("deskripsi")


        binding.textNamamateri.text = judul_materi
        binding.txtDescMateri.text = deskripsi

        binding.backArrow.setOnClickListener {
            onBackPressed()
        }

        binding.btnPerbarui.setOnClickListener {
            val intent = Intent(this, PerbaruiMateriActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("judul", judul_materi)
            intent.putExtra("deskripsi", deskripsi)
            startActivity(intent)
        }
        binding.btnHapus.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_delete)
            dialog.setCancelable(true)

            val btnYa = dialog.findViewById<Button>(R.id.btn_okay)
            val btnTidak = dialog.findViewById<Button>(R.id.btn_cancel)

            btnYa.setOnClickListener {
                deleteMateri()
                dialog.dismiss()
            }

            btnTidak.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        return super.getOnBackInvokedDispatcher()
        finish()

    }

    private fun deleteMateri() {
        val id = intent.getStringExtra("id")
        if (id != null) {
            val call = APIConfig.getService(this).deleteMateri(id)
            call.enqueue(object : Callback<MateriResponse> {
                override fun onResponse(call: Call<MateriResponse>, response: Response<MateriResponse>) {
                    if (response.isSuccessful) {
                        // Tambahkan kode untuk menangani jika delete berhasil
                        Toast.makeText(applicationContext, "Materi berhasil dihapus", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@DetailMateriActivity, KelolaMateriActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        startActivity(intent)

                    } else {
                        // Tambahkan kode untuk menangani jika delete gagal
                        Toast.makeText(applicationContext, "Gagal menghapus materi", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<MateriResponse>, t: Throwable) {
                    // Tambahkan kode untuk menangani jika terjadi kegagalan koneksi atau request
                    Toast.makeText(applicationContext, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            // Handle jika nilai ID tidak valid
            Toast.makeText(applicationContext, "ID materi tidak valid", Toast.LENGTH_SHORT).show()
        }
    }

}