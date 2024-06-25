package com.example.coachreport.siswa

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.coachreport.api.APIConfig
import com.example.coachreport.api.response.kelasResponse.DataItem
import com.example.coachreport.api.response.kelasResponse.KelasIndexResponse
import com.example.coachreport.databinding.ActivityTambahDataSiswaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahDataSiswaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTambahDataSiswaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityTambahDataSiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var nama = binding.edtNama.text
        var noIdentitas = binding.edtNoidentitas.text
        var noTelp = binding.edtNottelpon.text

        getKelas()
    }

    private fun dataSpinner(dataItem: List<DataItem>){

        val kelas: List<String> = dataItem.map { it.nama.toString() }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, kelas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.edtKelas.adapter = adapter
    }

    private fun getKelas(){
        APIConfig.getService(this).indexKelas().enqueue(object : Callback<KelasIndexResponse> {
            override fun onResponse(
                call: Call<KelasIndexResponse>,
                response: Response<KelasIndexResponse>
            ) {
                if (response.code() == 200) {
                    val data = response.body()
                    val dataItem = data?.data
                    Log.d("FETCH DATA", dataItem.toString())
                    dataItem?.let { dataSpinner(it as List<DataItem>) }
                }
            }
            override fun onFailure(call: Call<KelasIndexResponse>, t: Throwable) {
                Toast.makeText(this@TambahDataSiswaActivity,  t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}