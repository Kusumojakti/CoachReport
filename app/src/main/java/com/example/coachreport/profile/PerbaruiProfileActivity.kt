package com.example.coachreport.profile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coachreport.R
import com.example.coachreport.databinding.ActivityPerbaruiProfileBinding

class PerbaruiProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerbaruiProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerbaruiProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("name")
        val noHp = intent.getStringExtra("noHp")
        val email = intent.getStringExtra("email")

        binding.edtNama.setText(username.toString())
        binding.edtNoHP.setText(noHp.toString())
        binding.edtEmail.setText(email.toString())
    }
}