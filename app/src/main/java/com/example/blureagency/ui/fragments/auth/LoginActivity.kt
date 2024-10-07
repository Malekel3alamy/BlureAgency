package com.example.blureagency.ui.fragments.auth

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.Selection.setSelection
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.blureagency.R
import com.example.blureagency.databinding.ActivityLoginBinding
import com.example.blureagency.ui.MainActivity
import com.example.blureagency.ui.viewmodel.SignInViewModel
import com.example.blureagency.ui.viewmodel.SignupViewModel
import com.example.movies.utils.Resources
import com.example.storeapp.utils.RegisterValidation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(R.layout.activity_login) {
    private lateinit var binding : ActivityLoginBinding
    private val signInViewModel by viewModels<SignInViewModel>()

    private val signupViewModel by viewModels<SignupViewModel>()

    private var email = ""

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
             binding.loginLoginBtn.setOnClickListener {

                 email =  binding.loginEmailEt.text.toString()
                 val password = binding.loginPasswordEt.text.toString()

                 if (!signInViewModel.verifyEmailAndPasswordIsEmpty(email,password)){
                     Toast.makeText(this," please enter all fields",Toast.LENGTH_SHORT).show()
                 }else{
                     signInViewModel.signInWithEmailAndPassword(email, password)
                     lifecycleScope.launch {
                         signInViewModel.signInStatus.collectLatest {
                             when(it){
                                 is Resources.Error -> {
                                     lifecycleScope.launch (Dispatchers.Main){
                                         hidePR()
                                         Toast.makeText(this@LoginActivity,it.message.toString(),Toast.LENGTH_SHORT).show()
                                     }
                                 }
                                 is Resources.Loading -> {
                                     showPR()
                                 }
                                 is Resources.Success -> {
                                     val intent =Intent(this@LoginActivity,MainActivity::class.java)
                                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                     startActivity(intent)
                                 }
                             }
                         }
                     }
                 }


             }

        //  forget password
        binding.loginForgetPasswordTv.setOnClickListener {
            email =  binding.loginEmailEt.text.toString()
           if (email.isNotEmpty()){
               signInViewModel.resetPassword(email)
               lifecycleScope.launch {
                   signInViewModel.resetPassword.collectLatest {
                       when(it){
                           is Resources.Error -> {
                               Toast.makeText(this@LoginActivity," Failed To Send Email ",Toast.LENGTH_SHORT).show()
                           }
                           is Resources.Loading -> {
                               showPR()
                           }
                           is Resources.Success -> {
                               Toast.makeText(this@LoginActivity,"reset link was sent to your email",Toast.LENGTH_SHORT).show()

                           }
                       }
                   }
               }
           }else{
               Toast.makeText(this@LoginActivity,"enter email ",Toast.LENGTH_SHORT).show()

           }
        }

        // show and hide password
        var isPasswordVisible = false
        binding.loginShowPassword.setOnClickListener {

            if (isPasswordVisible){
                binding.loginPasswordEt.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                isPasswordVisible = false
            }else{
                binding.loginPasswordEt.inputType = InputType.TYPE_CLASS_TEXT or  InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                isPasswordVisible  = true

            }
            binding.loginPasswordEt.apply {
                setSelection(this.text.trim().length)
            }
        }


    }

    private fun showPR(){
        binding.loginBtnPr.visibility = View.VISIBLE
        binding.loginLoginBtn.visibility= View.INVISIBLE
    }

    private fun hidePR(){
        binding.loginBtnPr.visibility = View.INVISIBLE
        binding.loginLoginBtn.visibility= View.VISIBLE
    }

}