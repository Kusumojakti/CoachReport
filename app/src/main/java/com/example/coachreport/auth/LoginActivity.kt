package com.example.coachreport.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coachreport.DashboardActivity
import com.example.coachreport.R
import com.example.coachreport.api.APIConfig
import com.example.coachreport.api.response.LoginResponse
import com.example.coachreport.databinding.ActivityLoginBinding
import com.example.coachreport.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val nohp = binding.edtNohp.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            authlogin(nohp, password)
        }

        binding.txtRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun authlogin(noHp: String, password: String) {
        if (noHp.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Phone number and password must be filled", Toast.LENGTH_SHORT).show()
            return
        }

        APIConfig.getService().AuthLogin(noHp, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.code() == 200) {

                    val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Handle unsuccessful login
                    Toast.makeText(this@LoginActivity, "Login failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@LoginActivity, "Login error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
