package com.example.coachreport.materi

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachreport.R
import com.example.coachreport.adapter.adapterMateri
import com.example.coachreport.api.APIConfig
import com.example.coachreport.api.response.MateriResponse
import com.example.coachreport.databinding.ActivityKelolaMateriBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelolaMateriActivity : AppCompatActivity() {
    private lateinit var binding : ActivityKelolaMateriBinding
    private lateinit var adapterMateri: adapterMateri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelolaMateriBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterMateri = adapterMateri(this, ArrayList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapterMateri

        getData()
    }

    private fun getData() {
        APIConfig.getService(this).getMateri().enqueue(object : Callback<MateriResponse> {
            override fun onResponse(
                call: Call<MateriResponse>,
                response: Response<MateriResponse>
            ) {
                if (response.code() == 200) {
                    val responsemateri = response.body()
                    val datamateri = responsemateri?.user

                    if (datamateri != null) {
                        adapterMateri.updateData(datamateri)
                    }

                }
            }

            override fun onFailure(call: Call<MateriResponse>, t: Throwable) {
                Toast.makeText(this@KelolaMateriActivity,  "Data Not Found", Toast.LENGTH_LONG).show()
            }
        })
    }
}