package com.example.coachreport.siswa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coachreport.R
import com.example.coachreport.api.APIConfig
import com.example.coachreport.api.request.siswaRequest.SiswaStoreRequest
import com.example.coachreport.api.request.siswaRequest.SiswaUpdateRequest
import com.example.coachreport.api.response.kelasResponse.DataItem
import com.example.coachreport.api.response.kelasResponse.KelasIndexResponse
import com.example.coachreport.api.response.siswaResponse.SiswaUpdateResponse
import com.example.coachreport.databinding.ActivityPerbaruiDataSiswaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerbaruiDataSiswaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerbaruiDataSiswaBinding
    private var dataKelas: List<DataItem> = emptyList()
    private var namaKelas: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerbaruiDataSiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nama = intent.getStringExtra("nama")
        namaKelas = intent.getStringExtra("kelas").toString()
        val noIdentitas = intent.getStringExtra("noIdentitas")
        val noTelp = intent.getStringExtra("noTelp")

        binding.edtNama.setText(nama)
        binding.edtNottelpon.setText(noTelp)
        binding.edtNoidentitas.setText(noIdentitas)

        getKelas()


        var kelasId: Int? = null

        binding.edtKelas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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

        binding.btnPerbaruidata.setOnClickListener {
            var nama = binding.edtNama.text.toString()
            var noIdentitas = binding.edtNoidentitas.text.toString()
            var noTelp = binding.edtNottelpon.text.toString()
            val dataSiswa = SiswaUpdateRequest(
                nama = nama,
                jadwalKelasId = kelasId,
                noTelp = noTelp
            )
            updateSiswa(dataSiswa, noIdentitas)
        }

        binding.backArrow.setOnClickListener {
            onBackPressed()
        }
    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        return super.getOnBackInvokedDispatcher()
        finish()
    }

    private fun dataSpinner(dataItem: List<DataItem>){

        val kelas: List<String> = dataItem.map { it.nama.toString() }
        val defPosition = kelas.indexOf(namaKelas)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, kelas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.edtKelas.adapter = adapter

        if (defPosition != -1){
            binding.edtKelas.setSelection(defPosition)
        }
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
                    dataItem?.let { dataKelas= it as List<DataItem> }
                }
            }
            override fun onFailure(call: Call<KelasIndexResponse>, t: Throwable) {
                Toast.makeText(this@PerbaruiDataSiswaActivity,  t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateSiswa(datasiswa: SiswaUpdateRequest, noIdentitas: String) {
        APIConfig.getService(this).updateSiswa(noIdentitas, datasiswa).enqueue(object : Callback<SiswaUpdateResponse> {
            override fun onResponse(
                call: Call<SiswaUpdateResponse>,
                response: Response<SiswaUpdateResponse>
            ) {
                if (response.code() == 200) {
                    Toast.makeText(this@PerbaruiDataSiswaActivity,  "Data Berhasil Diperbarui", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@PerbaruiDataSiswaActivity, KelolaDataSiswaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<SiswaUpdateResponse>, t: Throwable) {
                Toast.makeText(this@PerbaruiDataSiswaActivity,  t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}