package com.example.coachreport.jadwal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachreport.adapter.adapterJadwal
import com.example.coachreport.api.APIConfig
import com.example.coachreport.api.response.kelasResponse.KelasIndexResponse
import com.example.coachreport.databinding.ActivityKelolaJadwalBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelolaJadwalActivity : AppCompatActivity() {
    private lateinit var binding : ActivityKelolaJadwalBinding
    private lateinit var adapterJadwal: adapterJadwal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelolaJadwalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterJadwal = adapterJadwal(this, ArrayList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapterJadwal

        binding.addJadwalBtn.setOnClickListener {
            val intent = Intent(this, TambahJadwalActivity::class.java)
            startActivity(intent)
        }

        getData()
    }

    private fun getData() {
        APIConfig.getService(this).indexKelas().enqueue(object : Callback<KelasIndexResponse> {
            override fun onResponse(
                call: Call<KelasIndexResponse>,
                response: Response<KelasIndexResponse>
            ) {
                if (response.code() == 200) {
                    val responsejadwal = response.body()
                    val datajadwal = responsejadwal?.data

                    if (datajadwal != null) {
                        adapterJadwal.updateData(datajadwal)
                    }
                }
            }

            override fun onFailure(call: Call<KelasIndexResponse>, t: Throwable) {
                Toast.makeText(this@KelolaJadwalActivity,  "Data Not Found", Toast.LENGTH_LONG).show()
            }
        })
    }
}