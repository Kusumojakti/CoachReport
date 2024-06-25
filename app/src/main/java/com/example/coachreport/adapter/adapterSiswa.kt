package com.example.coachreport.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coachreport.R
import com.example.coachreport.api.response.siswaResponse.DataItem
import com.example.coachreport.siswa.DetailDataSiswa

class adapterSiswa (private  val context: Context, private var siswaList: List<DataItem?>?) : RecyclerView.Adapter<adapterSiswa.myViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fetch_data_siswa, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentItem = siswaList?.get(position)

        val nama = currentItem?.nama
        val kelas = currentItem?.jadwalKelas?.nama

        holder.nama.text = nama
        holder.kelas.text = kelas
        holder.cardSiswa.setOnClickListener {
            val intent = Intent(context, DetailDataSiswa::class.java)
            intent.putExtra("nama", nama)
            intent.putExtra("kelas", kelas)
            intent.putExtra("noIdentitas", currentItem?.noIdentitas.toString())
            intent.putExtra("noTelp", currentItem?.noTelp)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return siswaList!!.size
    }

    fun updateData(newData: List<DataItem?>?){
        siswaList = newData
        notifyDataSetChanged()
    }

    class myViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val nama = view.findViewById<TextView>(R.id.txt_namasiswa)
        val kelas = view.findViewById<TextView>(R.id.txt_namakelas)
        val cardSiswa = view.findViewById<LinearLayout>(R.id.card_siswa)
    }
}