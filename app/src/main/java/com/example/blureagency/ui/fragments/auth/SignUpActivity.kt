package com.example.blureagency.ui.fragments.auth

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.blureagency.R
import com.example.blureagency.databinding.ActivitySignUpBinding
import com.example.blureagency.model.User
import com.example.blureagency.ui.viewmodel.AuthViewModel
import com.example.movies.utils.Resources
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity(R.layout.activity_sign_up) {
lateinit var binding :ActivitySignUpBinding

private val authViewModel by viewModels<AuthViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // connect country code picker to edit text phone

        binding.ccp.registerCarrierNumberEditText(binding.signupPhoneNumberEt)

        binding.signupShowPassword.setOnClickListener {
            var isPasswordVisible = false
            if (isPasswordVisible){
               binding.signupPasswordEt.inputType =  InputType.TYPE_TEXT_VARIATION_PASSWORD // InputType.T
                 isPasswordVisible = false
            }else{
                binding.signupPasswordEt.inputType =  InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            isPasswordVisible  = true// InputType.T

            }
            binding.signupPasswordEt.apply {
                setSelection(this.text.trim().length)
            }
        }


       binding.signupSignupBtn.setOnClickListener {
           val email = binding.signupEmailEt.text.toString().trim()
           val password  = binding.signupPasswordEt.text.toString().trim()
           val  userName = binding.signupUserNameEt.text.toString().trim()
           val phoneNumber = binding.ccp.fullNumber
           Log.d("PhoneNumber",phoneNumber)

           val user = User(userName,phoneNumber, email, password)


           if (authViewModel.isSignupFieldsNotEmpty(user)){
               if (!isPasswordValid(user)){
                   binding.signupPasswordEt.error = "password must be more 6 digits"
               }
               if (!isEmailValid(user)){
                   binding.signupEmailEt.error = "not valid email"
               }
               if (!isPhoneNumberValid(user)){
                   binding.signupPhoneNumberEt.error = "not valid phone number"
               }

               if (isEmailValid(user) && isPasswordValid(user) && isPhoneNumberValid(user)){
                   authViewModel.signupWithFirebase(email, password)
                   lifecycleScope.launch {
                       authViewModel.signupStatus.collectLatest {
                           when(it){
                               is Resources.Error -> {
                                   hidePR()
                                   Toast.makeText(this@SignUpActivity,"Error : ${it.message}",Toast.LENGTH_SHORT).show()
                               }
                               is Resources.Loading -> {
                                   showPR()
                               }
                               is Resources.Success -> {
                                   hidePR()
                                   Toast.makeText(this@SignUpActivity,"signup succeeded",Toast.LENGTH_SHORT).show()
                                   finish()
                               }
                           }
                       }
                   }
               }
           }else{
               Toast.makeText(this@SignUpActivity,"make sure to enter all fields",Toast.LENGTH_SHORT).show()
           }
       }
    }

    private fun  isEmailValid(user: User):Boolean{
        var emailValid  = authViewModel.checkEmail(user)

       return emailValid
    }

    private fun  isPhoneNumberValid(user: User):Boolean{
        var phoneNumberValid  = authViewModel.checkPhoneNumber(user)

        return phoneNumberValid
    }

    private fun isPasswordValid(user: User):Boolean{
        var passwordValid  = authViewModel.checkPassword(user)
        return passwordValid
    }
    private fun showPR(){
        binding.signupBtnPr.visibility = View.VISIBLE
        binding.signupSignupBtn.visibility= View.INVISIBLE
    }

    private fun hidePR(){
        binding.signupBtnPr.visibility = View.INVISIBLE
        binding.signupSignupBtn.visibility= View.VISIBLE
    }

}