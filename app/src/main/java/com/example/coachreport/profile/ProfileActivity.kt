package com.example.coachreport.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coachreport.R
import com.example.coachreport.auth.LoginActivity
import com.example.coachreport.databinding.ActivityProfileBinding
import com.example.coachreport.utils.SessionManager

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sessionManager = SessionManager
        val username = sessionManager.getName(this)
        val noHp = sessionManager.getnohp(this)
        val email = sessionManager.getEmail(this)

        binding.textNama.text = username
        binding.noHp.text = noHp
        binding.emailuser.text = email

        binding.btnPerbaruiProfile.setOnClickListener {
            val intent = Intent(this, PerbaruiProfileActivity::class.java)
            intent.putExtra("name", username)
            intent.putExtra("noHp", noHp)
            intent.putExtra("email", email)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            sessionManager.clearData(this)
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }
    }
}