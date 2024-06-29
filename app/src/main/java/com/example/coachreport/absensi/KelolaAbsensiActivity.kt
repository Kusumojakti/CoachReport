package com.example.coachreport.absensi

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachreport.R
import com.example.coachreport.adapter.adapterAbsensi
import com.example.coachreport.api.APIConfig
import com.example.coachreport.api.response.absensiResponse.AbsensiGetResponse
import com.example.coachreport.api.response.absensiResponse.AbsensiItem
import com.example.coachreport.api.response.absensiResponse.SiswaItem
import com.example.coachreport.api.response.kelasResponse.DataItem
import com.example.coachreport.api.response.kelasResponse.KelasIndexResponse
import com.example.coachreport.databinding.ActivityKelolaAbsensiBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelolaAbsensiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKelolaAbsensiBinding
    private lateinit var adapter: adapterAbsensi
    private lateinit var dropdown: Spinner
    private var dataKelas: List<DataItem> = emptyList()
    private var selectedPertemuan: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelolaAbsensiBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getKelas()

        var kelasId: Int? = null

        binding.pilihankelas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedDataItem = dataKelas[position]
                kelasId = selectedDataItem.id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val pertemuanArray = resources.getIntArray(R.array.pilih_pertemuan)
        val pertemuanList = pertemuanArray.toList()
        dropdown = findViewById(R.id.pilihanpertemuan)
        if (dropdown != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, pertemuanList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dropdown.adapter = adapter
        }

        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedPertemuan = pertemuanList[position]
                getAbsensi(selectedPertemuan, kelasId)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Handle the case when nothing is selected
            }
        }
    }

    private fun dataSpinner(dataItem: List<DataItem>) {
        val kelas: List<String> = dataItem.map { it.nama.toString() }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, kelas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.pilihankelas.adapter = adapter
    }

    private fun getKelas() {
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
                    dataItem?.let { dataKelas = it as List<DataItem> }
                }
            }

            override fun onFailure(call: Call<KelasIndexResponse>, t: Throwable) {
                Toast.makeText(this@KelolaAbsensiActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setupRecyclerView(absensi: List<AbsensiItem?>, siswa: List<SiswaItem?>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = adapterAbsensi(this, absensi, siswa)
        binding.recyclerView.adapter = adapter
    }

    private fun getAbsensi(pertemuanKe: Int?, jadwalKelasId: Int?) {
        APIConfig.getService(this).getabsensi(pertemuanKe, jadwalKelasId)
            .enqueue(object : Callback<AbsensiGetResponse> {
                override fun onResponse(call: Call<AbsensiGetResponse>, response: Response<AbsensiGetResponse>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        data?.let {
                            Log.d("FETCH ABSENSI", it.data?.absensi.toString())
                            it.data?.let { data ->
                                setupRecyclerView(data.absensi ?: listOf(), data.siswa ?: listOf())
                            }
                        }
                    } else {
                        // Log the response details for debugging
                        Log.e("FETCH ABSENSI", "Error: ${response.code()} - ${response.message()}")
                        Toast.makeText(this@KelolaAbsensiActivity, "Gagal mendapatkan data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AbsensiGetResponse>, t: Throwable) {
                    // Log the failure reason for debugging
                    Log.e("FETCH ABSENSI", "Failure: ${t.message}")
                    Toast.makeText(this@KelolaAbsensiActivity, "Gagal mendapatkan data", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun saveabsen()

}
