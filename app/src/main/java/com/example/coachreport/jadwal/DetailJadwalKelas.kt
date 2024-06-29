package com.example.coachreport.jadwal

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coachreport.R
import com.example.coachreport.api.APIConfig
import com.example.coachreport.api.response.kelasResponse.KelasIndexResponse
import com.example.coachreport.databinding.ActivityDetailJadwalKelasBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailJadwalKelas : AppCompatActivity() {
    private lateinit var binding: ActivityDetailJadwalKelasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailJadwalKelasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id")
        val hari = intent.getStringExtra("hari")
        val waktumulai = intent.getStringExtra("mulai")
        val waktuselesai = intent.getStringExtra("selesai")
        val kelas = intent.getStringExtra("nama")
        val tempat = intent.getStringExtra("tempat")
        val materi = intent.getStringExtra("judul")

        binding.textMateri.setText(materi.toString())
        binding.hariJadwal.setText(hari.toString())
        binding.waktuMulai.setText(waktumulai.toString())
        binding.waktuSelesai.setText(waktuselesai.toString())
        binding.tempatKelas.setText(tempat.toString())
        binding.namaKelas.setText(kelas.toString())

        binding.btnPerbaruiJadwal.setOnClickListener {
            val intent = Intent(this, PerbaruiJadwalActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("hari", hari)
            intent.putExtra("mulai", waktumulai)
            intent.putExtra("selesai", waktuselesai)
            intent.putExtra("nama", kelas)
            intent.putExtra("tempat", tempat)
            intent.putExtra("judul", materi)
            startActivity(intent)
        }

        binding.btnHapusMateri.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_delete)
            dialog.setCancelable(true)

            val btnYa = dialog.findViewById<Button>(R.id.btn_okay)
            val btnTdk = dialog.findViewById<Button>(R.id.btn_cancel)

            btnYa.setOnClickListener {
                deletejadwal()
                dialog.dismiss()
            }

            btnTdk.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }

    }

    private fun deletejadwal() {
        val id = intent.getStringExtra("id")
        if (id != null) {
            APIConfig.getService(this).deleteJadwal(id).enqueue(object : Callback<KelasIndexResponse>{
                override fun onResponse(
                    call: Call<KelasIndexResponse>,
                    response: Response<KelasIndexResponse>
                ) {
                    if (response.code() == 200) {
                        Toast.makeText(applicationContext, "Jadwal Berhasil Dihapus", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@DetailJadwalKelas, KelolaJadwalActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    Toast.makeText(applicationContext, "Gagal menghapus jadwal", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<KelasIndexResponse>, t: Throwable) {
                    Toast.makeText(this@DetailJadwalKelas,  t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}