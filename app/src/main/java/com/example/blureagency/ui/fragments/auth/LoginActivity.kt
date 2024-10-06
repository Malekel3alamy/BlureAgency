package com.example.blureagency.ui.fragments.auth

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.blureagency.R
import com.example.blureagency.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity(R.layout.activity_login) {
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // moving to sign up fragment
        binding.loginSignupTv.setOnClickListener {
            val  i  = Intent(this,SignUpActivity::class.java)
            startActivity(i)
        }

        // check email and password are not empty



    }

}