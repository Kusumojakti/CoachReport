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
import com.example.coachreport.api.request.jadwalRequest.KelasUpdateRequest
import com.example.coachreport.api.response.MateriResponse
import com.example.coachreport.api.response.dataMateri
import com.example.coachreport.api.response.kelasResponse.KelasUpdateResponse
import com.example.coachreport.databinding.ActivityPerbaruiJadwalBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PerbaruiJadwalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerbaruiJadwalBinding
    private var dataMateri: List<dataMateri> = emptyList()
    private lateinit var dropdown: Spinner
    private var selectedHari: String? = null
    private var materiId: Int? = null
    private var mulaiwaktu: String? = null
    private var selesaiwaktu: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerbaruiJadwalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id") ?: ""
        val waktumulai = intent.getStringExtra("mulai") ?: ""
        val waktuselesai = intent.getStringExtra("selesai") ?: ""
        val kelas = intent.getStringExtra("nama") ?: ""
        val tempat = intent.getStringExtra("tempat") ?: ""
        val materi = intent.getStringExtra("judul") ?: ""

        mulaiwaktu = waktumulai
        selesaiwaktu = waktuselesai

        val waktuMulaiFormatted = convertTimeFormat(waktumulai)
        val waktuSelesaiFormatted = convertTimeFormat(waktuselesai)

        binding.inputwaktu.setText(waktuMulaiFormatted)
        binding.outputwaktu.setText(waktuSelesaiFormatted)
        binding.edtPerbaruikelas.setText(kelas)
        binding.edtTempat.setText(tempat)

        binding.backArrow.setOnClickListener {
            onBackInvokedDispatcher
        }

        binding.edtMateri.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedDataItem = dataMateri[position]
                materiId = selectedDataItem.id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        val hariArray = resources.getStringArray(R.array.pilih_hari)
        dropdown = findViewById(R.id.edt_hari)
        if (dropdown != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, hariArray)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dropdown.adapter = adapter
        }

        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedHari = hariArray[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
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

        binding.btnPerbaruidata.setOnClickListener {
            val tempat = binding.edtTempat.text.toString().trim()
            val kelas = binding.edtPerbaruikelas.text.toString().trim()
            val datajadwal = KelasUpdateRequest(
                nama = kelas,
                hari = selectedHari,
                materisId = materiId,
                tempat = tempat,
                mulai = mulaiwaktu ?: "",
                selesai = selesaiwaktu ?: ""
            )

            updateJadwal(datajadwal, id)
        }
    }

    private fun convertTimeFormat(time: String): String {
        return try {
            val inputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val date = inputFormat.parse(time)
            outputFormat.format(date ?: "")
        } catch (e: Exception) {
            time // Return original time if parsing fails
        }
    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        return super.getOnBackInvokedDispatcher()
        finish()
    }

    private fun dataSpinner(dataItem: List<dataMateri>) {
        val materi: List<String> = dataItem.map { it.judul.toString() }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, materi)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.edtMateri.adapter = adapter
    }

    private fun getMateri() {
        APIConfig.getService(this).getMateri().enqueue(object : Callback<MateriResponse> {
            override fun onResponse(call: Call<MateriResponse>, response: Response<MateriResponse>) {
                if (response.code() == 200) {
                    val data = response.body()
                    val dataItem = data?.user
                    Log.d("FETCH DATA", dataItem.toString())
                    dataItem?.let { dataSpinner(it as List<dataMateri>) }
                    dataItem?.let { dataMateri = it as List<dataMateri> }
                }
            }

            override fun onFailure(call: Call<MateriResponse>, t: Throwable) {
                Toast.makeText(this@PerbaruiJadwalActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateJadwal(dataJadwal: KelasUpdateRequest, id: String) {
        APIConfig.getService(this).updatejadwal(id, dataJadwal).enqueue(object : Callback<KelasUpdateResponse> {
            override fun onFailure(call: Call<KelasUpdateResponse>, t: Throwable) {
                Toast.makeText(this@PerbaruiJadwalActivity, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<KelasUpdateResponse>,
                response: Response<KelasUpdateResponse>
            ) {
                if (response.code() == 200) {
                    Toast.makeText(this@PerbaruiJadwalActivity, "Data Jadwal berhasil diperbarui", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@PerbaruiJadwalActivity, KelolaJadwalActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                } else {
                    Toast.makeText(this@PerbaruiJadwalActivity, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
