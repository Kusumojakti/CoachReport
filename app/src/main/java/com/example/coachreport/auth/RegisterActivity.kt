package com.example.coachreport.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coachreport.R
import com.example.coachreport.api.APIConfig
import com.example.coachreport.api.request.RegisterRequest
import com.example.coachreport.api.response.RegisterResponse
import com.example.coachreport.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val nama = binding.edtNama.text.toString().trim()
            val noHp = binding.edtNottelpon.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            val confirmpass = binding.edtConfirmpassword.text.toString().trim()
            authregister(nama, noHp, email, password, confirmpass)
        }

        binding.txtLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun authregister(nama: String, noHp : String, email: String, password: String, confirmpass: String) {
        if (noHp.isEmpty() || password.isEmpty() || nama.isEmpty() || email.isEmpty() || confirmpass.isEmpty()) {
            Toast.makeText(this, "All field must be required", Toast.LENGTH_SHORT).show()
            return
        }
        var regisInfo: RegisterRequest{

        }
        APIConfig.getService().AuthRegist(regisInfo).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.code() == 200) {
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Register Failed", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Register error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}