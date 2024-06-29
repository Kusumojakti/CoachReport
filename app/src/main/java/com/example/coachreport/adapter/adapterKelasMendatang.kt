package com.example.coachreport.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coachreport.R
import com.example.coachreport.api.response.kelasResponse.DataItems

class adapterKelasMendatang (private val context: Context, private var comingclasslist : List<DataItems?>?) : RecyclerView.Adapter<adapterKelasMendatang.myViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterKelasMendatang.myViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fetch_oncoming_class, parent,false)
        return adapterKelasMendatang.myViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterKelasMendatang.myViewHolder, position: Int) {
        val currentItem = comingclasslist?.get(position)

        val id = currentItem?.id
        val kelas = currentItem?.nama
        val nama_materi = currentItem?.materis?.judul
        val hari = currentItem?.hari
        val mulai = currentItem?.mulai
        val selesai = currentItem?.selesai
        val tempat = currentItem?.tempat

        holder.nama_materi.text = nama_materi
        holder.kelas.text = kelas
        holder.hari.text = hari
        holder.mulai.text = mulai
        holder.selesai.text = selesai
    }

    override fun getItemCount(): Int {
        return comingclasslist!!.size
    }

    fun updateData(newData: List<DataItems?>?) {
        comingclasslist = newData
        notifyDataSetChanged()
    }
    class myViewHolder ( view: View) : RecyclerView.ViewHolder(view) {
        val nama_materi = view.findViewById<TextView>(R.id.txt_namamaterijadwal)
        val kelas = view.findViewById<TextView>(R.id.txt_namakelas)
        val hari = view.findViewById<TextView>(R.id.txt_namahari)
        val mulai = view.findViewById<TextView>(R.id.txt_waktumulai)
        val selesai = view.findViewById<TextView>(R.id.txt_waktuselesai)
        val card_jadwal = view.findViewById<LinearLayout>(R.id.card_jadwal)
    }
}