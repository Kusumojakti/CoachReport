package com.example.coachreport.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coachreport.R
import com.example.coachreport.api.response.absensiResponse.AbsensiItem
import com.example.coachreport.api.response.absensiResponse.SiswaItem

class adapterAbsensi(
    private val context: Context,
    private var absensiList: List<AbsensiItem?>?,
    private var siswaList: List<SiswaItem?>,
) : RecyclerView.Adapter<adapterAbsensi.MyViewHolder>() {

    private val statusList = arrayOf("hadir", "alpha", "ijin")
    private val updatedAbsensiList = mutableListOf<AbsensiItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fetch_absensi_siswa, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val siswaItem = siswaList?.get(position)

        holder.nama.text = siswaItem?.noIdentitas
        holder.kelas.text = siswaItem?.nama

        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, statusList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.status.adapter = adapter

        val absensiItems = absensiList?.find { it?.siswasId == siswaItem?.noIdentitas }

        val statusIndex = absensiItems?.let { statusList.indexOf(it.status?.toLowerCase()) } ?: 1
        holder.status.setSelection(statusIndex)

        if (absensiItems != null) {
            updatedAbsensiList.add(absensiItems)
        }
        holder.status.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                pos: Int,
                id: Long
            ) {
                val newStatus = statusList[pos]
                val siswaId = siswaItem?.noIdentitas
                val item = updatedAbsensiList.find { it.siswasId == siswaId }
                if (item != null) {
                    Log.d("Hasil Item", item.toString())
                    item.status = newStatus
                } else {
                    val newAbsensiItem = AbsensiItem(
                        siswasId = siswaId ?: "",
                        status = newStatus
                    )
                    updatedAbsensiList.add(newAbsensiItem)
                }
                Log.d("Data Status", updatedAbsensiList.toString())
                Log.d("Status", newStatus)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }


    override fun getItemCount(): Int {
        return siswaList!!.size
    }

    fun getUpdatedAbsensiList(): List<AbsensiItem> {
        return updatedAbsensiList
    }

    fun updateData(newAbsensi: List<AbsensiItem>, newSiswa: List<SiswaItem>) {
        absensiList = newAbsensi
        siswaList = newSiswa
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nama: TextView = view.findViewById(R.id.txt_namasiswa)
        val kelas: TextView = view.findViewById(R.id.txt_namakelas)
        val status: Spinner = view.findViewById(R.id.spin_absen)
    }
}
