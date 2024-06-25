package com.example.coachreport.siswa

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachreport.R
import com.example.coachreport.adapter.adapterSiswa
import com.example.coachreport.api.APIConfig
import com.example.coachreport.api.response.siswaResponse.SiswaIndexResponse
import com.example.coachreport.databinding.ActivityKelolaDataSiswaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelolaDataSiswaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityKelolaDataSiswaBinding
    private lateinit var adapterSiswa: adapterSiswa
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityKelolaDataSiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterSiswa = adapterSiswa(this, ArrayList())
        binding.rcDatasiswa.layoutManager = LinearLayoutManager(this)
        binding.rcDatasiswa.adapter = adapterSiswa

        binding.addSiswaBtn.setOnClickListener{
            val intent = Intent(this, TambahDataSiswaActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        APIConfig.getService(this).indexSiswa().enqueue(object : Callback<SiswaIndexResponse>{
            override fun onResponse(
                call: Call<SiswaIndexResponse>,
                response: Response<SiswaIndexResponse>
            ) {
                if (response.code() == 200) {
                    val dataSiswa = response?.body()?.data

                    if (!dataSiswa.isNullOrEmpty()) adapterSiswa.updateData(dataSiswa)
                }
            }

            override fun onFailure(call: Call<SiswaIndexResponse>, t: Throwable) {
                Toast.makeText(this@KelolaDataSiswaActivity,  t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}