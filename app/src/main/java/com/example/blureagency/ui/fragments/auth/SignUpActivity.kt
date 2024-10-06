package com.example.blureagency.ui.fragments.auth

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.blureagency.R
import com.example.blureagency.databinding.ActivityLoginBinding
import com.example.blureagency.databinding.ActivitySignUpBinding


class SignUpActivity : AppCompatActivity(R.layout.activity_sign_up) {
lateinit var binding :ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}