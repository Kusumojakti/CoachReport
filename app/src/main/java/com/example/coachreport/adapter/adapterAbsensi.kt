package com.example.coachreport.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coachreport.R
import com.example.coachreport.api.response.absensiResponse.AbsensiItem
import com.example.coachreport.api.response.absensiResponse.SiswaItem
import com.example.coachreport.api.response.absensiResponse.Ulasan

class adapterAbsensi(
    private val context: Context,
    private var absensiList: List<AbsensiItem?>?,
    private var siswaList: List<SiswaItem?>,
) : RecyclerView.Adapter<adapterAbsensi.MyViewHolder>() {

    private val statusList = arrayOf("hadir", "alpha", "ijin")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fetch_absensi_siswa, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val absensiItem = absensiList?.get(position)
//        val siswaItem = siswaList.find { it?.noIdentitas == absensiItem?.siswasId }
//
//        holder.nama.text = siswaItem?.nama ?: ""
//        holder.kelas.text = siswaItem?.nama ?: ""
//
//        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, statusList)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        holder.status.adapter = adapter
//
//        // Set Spinner value based on attendance status
//        val statusIndex = statusList.indexOf(absensiItem?.status ?: "")
//        holder.status.setSelection(if (statusIndex >= 0) statusIndex else 0)
        val absensiItem = absensiList?.get(position)
        val siswaItem = siswaList?.get(position)

        holder.nama.text = siswaItem?.noIdentitas
        holder.kelas.text = siswaItem?.nama
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, statusList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.status.adapter = adapter

        // Set Spinner value based on attendance status
        val statusIndex = statusList.indexOf(absensiItem?.status ?: "")
        holder.status.setSelection(if (statusIndex >= 0) statusIndex else 0)
    }

    override fun getItemCount(): Int {
        return absensiList!!.size
        return siswaList!!.size
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
