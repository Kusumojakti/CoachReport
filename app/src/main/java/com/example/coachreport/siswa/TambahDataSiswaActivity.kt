package com.example.coachreport.siswa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.coachreport.api.APIConfig
import com.example.coachreport.api.request.siswaRequest.SiswaStoreRequest
import com.example.coachreport.api.response.kelasResponse.DataItem
import com.example.coachreport.api.response.kelasResponse.KelasIndexResponse
import com.example.coachreport.api.response.siswaResponse.SiswaStoreResponse
import com.example.coachreport.databinding.ActivityTambahDataSiswaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahDataSiswaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTambahDataSiswaBinding
    private var dataKelas: List<DataItem> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityTambahDataSiswaBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.btnSimpanSiswa.setOnClickListener {
            var nama = binding.edtNama.text.toString()
            var noIdentitas = binding.edtNoidentitas.text.toString()
            var noTelp = binding.edtNottelpon.text.toString()
            val dataSiswa = SiswaStoreRequest(
                nama = nama,
                noIdentitas = noIdentitas,
                noTelp = noTelp,
                jadwalKelasId = kelasId
            )

            storeSiswa(dataSiswa)
        }

    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        return super.getOnBackInvokedDispatcher()
        finish()

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
                    dataItem?.let { dataKelas= it as List<DataItem> }
                }
            }
            override fun onFailure(call: Call<KelasIndexResponse>, t: Throwable) {
                Toast.makeText(this@TambahDataSiswaActivity,  t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun storeSiswa(dataSiswa: SiswaStoreRequest) {
        APIConfig.getService(this).storeSiswa(dataSiswa).enqueue(object : Callback<SiswaStoreResponse>{
            override fun onResponse(
                call: Call<SiswaStoreResponse>,
                response: Response<SiswaStoreResponse>
            ) {
                if (response.code() == 200) {
                    Toast.makeText(this@TambahDataSiswaActivity, response.message(), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@TambahDataSiswaActivity, KelolaDataSiswaActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                }
                Toast.makeText(this@TambahDataSiswaActivity, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<SiswaStoreResponse>, t: Throwable) {
                Toast.makeText(this@TambahDataSiswaActivity,  t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

}