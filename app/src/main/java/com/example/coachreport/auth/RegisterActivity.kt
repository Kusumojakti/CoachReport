package com.example.coachreport.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
            Log.d("RegisterActivity", "Register button clicked")
            val name = binding.edtNama.text.toString().trim()
            val noHp = binding.edtNottelpon.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            val password_confirmation = binding.edtConfirmpassword.text.toString().trim()
            Authregister(name, noHp, email, password, password_confirmation)
        }

        binding.txtLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun Authregister(name: String, noHp : String, email: String, password: String, password_confirmation: String) {
        if (noHp.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty() || password_confirmation.isEmpty()) {
            Toast.makeText(this, "All field must be required", Toast.LENGTH_SHORT).show()
            return
        }
        APIConfig.getService(this).AuthRegist(name, noHp, email, password, password_confirmation).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                Log.d("RegisterActivity", "onResponse: " + response.code())
                if (response.isSuccessful) {
                    Log.d("RegisterActivity", "Register successful")
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.d("RegisterActivity", "Register failed: " + response.errorBody()?.string())
                    Toast.makeText(this@RegisterActivity, "Register Failed", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("RegisterActivity", "Register error", t)
                Toast.makeText(this@RegisterActivity, "Register error", Toast.LENGTH_SHORT).show()
            }
        })

    }
}