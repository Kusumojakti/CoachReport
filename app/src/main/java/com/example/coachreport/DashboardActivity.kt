package com.example.coachreport

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coachreport.databinding.ActivityDashboardBinding
import com.example.coachreport.materi.KelolaMateriActivity
import com.example.coachreport.utils.SessionManager

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = SessionManager
        val username = sharedPreferences.getName(this)
        binding.txtusername.text = username

        binding.menumateri.setOnClickListener {
            val intent = Intent(this, KelolaMateriActivity::class.java)
            startActivity(intent)
        }
    }
}