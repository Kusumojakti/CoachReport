package com.example.coachreport.siswa

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coachreport.R
import com.example.coachreport.databinding.ActivityKelolaDataSiswaBinding

class KelolaDataSiswaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityKelolaDataSiswaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_kelola_data_siswa)

    }
}