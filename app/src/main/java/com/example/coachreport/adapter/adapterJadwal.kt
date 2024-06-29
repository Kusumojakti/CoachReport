package com.example.coachreport.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coachreport.R
import com.example.coachreport.api.response.kelasResponse.DataItem
import com.example.coachreport.jadwal.DetailJadwalKelas

class adapterJadwal (private val context: Context, private var jadwallist : List<DataItem?>?) : RecyclerView.Adapter<adapterJadwal.myViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterJadwal.myViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fetch_jadwal, parent,false)
        return adapterJadwal.myViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterJadwal.myViewHolder, position: Int) {
        val currentItem = jadwallist?.get(position)

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
        holder.card_jadwal.setOnClickListener {
            val intent = Intent(context, DetailJadwalKelas::class.java)
            intent.putExtra("id", id.toString())
            intent.putExtra("nama", kelas)
            intent.putExtra("judul", nama_materi)
            intent.putExtra("hari", hari)
            intent.putExtra("tempat", tempat)
            intent.putExtra("mulai", mulai)
            intent.putExtra("selesai", selesai)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return jadwallist!!.size
    }

    fun updateData(newData: List<DataItem?>?) {
        jadwallist = newData
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