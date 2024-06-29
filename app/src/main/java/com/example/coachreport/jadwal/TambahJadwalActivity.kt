package com.example.coachreport.jadwal

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.coachreport.R
import com.example.coachreport.api.APIConfig
import com.example.coachreport.api.request.jadwalRequest.KelasStoreRequest
import com.example.coachreport.api.response.MateriResponse
import com.example.coachreport.api.response.dataMateri
import com.example.coachreport.databinding.ActivityTambahJadwalBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class TambahJadwalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTambahJadwalBinding
    private var dataMateri : List<dataMateri> = emptyList()
    private lateinit var dropdown : Spinner
    private var selectedHari: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahJadwalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var materiId: Int? = null
        var mulaiwaktu: String? = null
        var selesaiwaktu : String? = null

            binding.edtMateri.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedDataItem = dataMateri[position]
                    materiId = selectedDataItem.id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }


        val hari = resources.getStringArray(R.array.pilih_hari)
        dropdown = findViewById(R.id.edt_hari)
        if (dropdown != null) {
            val adapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, hari)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dropdown.adapter = adapter
        }
        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedHari = hari[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Tidak ada tindakan
            }
        }
        getMateri()

        binding.inputwaktu.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeInListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                mulaiwaktu = String.format("%02d:%02d", hour, minute)
                binding.inputwaktu.setText(mulaiwaktu)
            }
            TimePickerDialog(this, timeInListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        binding.outputwaktu.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeOutListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                selesaiwaktu = String.format("%02d:%02d", hour, minute)
                binding.outputwaktu.setText(selesaiwaktu)
            }
            TimePickerDialog(this, timeOutListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        binding.btnSimpanSiswa.setOnClickListener {
            val tempat = binding.edtTempat.text.toString().trim()
            val kelas = binding.edtNamakelasjadwal.text.toString().trim()
            val datajadwal = KelasStoreRequest (
                nama = kelas,
                hari = selectedHari,
                materisId = materiId,
                tempat = tempat,
                mulai = mulaiwaktu,
                selesai = selesaiwaktu
            )

            addJadwal(datajadwal)
        }
    }
    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        return super.getOnBackInvokedDispatcher()
        finish()

    }

    private fun dataSpinner(dataItem: List<dataMateri>) {
        val materi : List<String> = dataItem.map { it.judul.toString() }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, materi)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.edtMateri.adapter = adapter
    }

    private fun getMateri() {
        APIConfig.getService(this).getMateri().enqueue(object : Callback<MateriResponse>{
            override fun onResponse(
                call: Call<MateriResponse>,
                response: Response<MateriResponse>
            ) {
                if (response.code() == 200) {
                    val data = response.body()
                    val dataItem = data?.user
                    Log.d("FETCH DATA", dataItem.toString())
                    dataItem?.let { dataSpinner(it as List<dataMateri>) }
                    dataItem?.let { dataMateri = it as List<dataMateri> }

                }
            }

            override fun onFailure(call: Call<MateriResponse>, t: Throwable) {
                Toast.makeText(this@TambahJadwalActivity,  t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun addJadwal(dataJadwal : KelasStoreRequest) {
        APIConfig.getService(this).addJadwalKelas(dataJadwal).enqueue(object : Callback<KelasStoreRequest>{
            override fun onResponse(
                call: Call<KelasStoreRequest>,
                response: Response<KelasStoreRequest>
            ) {
                if (response.code() == 200) {
                    Toast.makeText(this@TambahJadwalActivity, "Data Jadwal berhasil ditambah", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@TambahJadwalActivity,KelolaJadwalActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                }
                Toast.makeText(this@TambahJadwalActivity, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<KelasStoreRequest>, t: Throwable) {
                Toast.makeText(this@TambahJadwalActivity,  t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}