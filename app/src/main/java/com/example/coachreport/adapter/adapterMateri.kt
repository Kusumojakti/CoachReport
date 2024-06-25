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
import com.example.coachreport.api.response.UserItem
import com.example.coachreport.materi.DetailMateriActivity

class adapterMateri (private val context: Context, private var materilist : List<UserItem?>?) : RecyclerView.Adapter<adapterMateri.myViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterMateri.myViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fetch_materi, parent,false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterMateri.myViewHolder, position: Int) {
        val currentItem = materilist?.get(position)

        val judul = currentItem?.judul
        val deskripsi = currentItem?.deskripsi

        holder.tittle_materi.text = judul
        holder.card_materi.setOnClickListener {
            val intent = Intent(context, DetailMateriActivity::class.java)
            intent.putExtra("judul", judul)
            intent.putExtra("deskripsi", deskripsi)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return materilist!!.size
    }
    fun updateData(newData: List<UserItem?>?) {
        materilist = newData
        notifyDataSetChanged()
    }

    class myViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        val tittle_materi = view.findViewById<TextView>(R.id.txt_namamateri)
        val card_materi = view.findViewById<LinearLayout>(R.id.card_materi)
    }
}