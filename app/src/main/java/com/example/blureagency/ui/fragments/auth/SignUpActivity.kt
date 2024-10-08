package com.example.blureagency.ui.fragments.auth

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.blureagency.R
import com.example.blureagency.databinding.ActivitySignUpBinding
import com.example.blureagency.model.User
import com.example.blureagency.ui.MainActivity
import com.example.blureagency.ui.viewmodel.SignupViewModel
import com.example.movies.utils.Resources
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity(R.layout.activity_sign_up) {
private lateinit var binding :ActivitySignUpBinding

private val signupViewModel by viewModels<SignupViewModel>()

    companion object {
        const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // connect country code picker to edit text phone

        binding.ccp.registerCarrierNumberEditText(binding.signupPhoneNumberEt)
        var isPasswordVisible = false
        binding.signupShowPassword.setOnClickListener {

            if (isPasswordVisible){
               binding.signupPasswordEt.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                 isPasswordVisible = false
            }else{
                binding.signupPasswordEt.inputType = InputType.TYPE_CLASS_TEXT or  InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            isPasswordVisible  = true

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


           if (signupViewModel.isSignupFieldsNotEmpty(user)){
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
                   signupViewModel.signupWithFirebase(binding.signupPhoneNumberEt,user)
                   lifecycleScope.launch {
                       signupViewModel.signupStatus.collectLatest {
                           when(it){
                               is Resources.Error -> {
                                   hidePR()
                                   Toast.makeText(this@SignUpActivity,"Error : ${it.message}",Toast.LENGTH_SHORT).show()
                               }
                               is Resources.Loading -> {
                                   showPR()
                                   delay(2000L)
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


     /*   binding.signupGoogleIcon.setOnClickListener {
            signUpWithGoogle()
        }*/
    }

    private fun  isEmailValid(user: User):Boolean{
        var emailValid  = signupViewModel.checkEmail(user)

       return emailValid
    }

    private fun  isPhoneNumberValid(user: User):Boolean{
        var phoneNumberValid  = signupViewModel.checkPhoneNumber(user)

        return phoneNumberValid
    }

    private fun isPasswordValid(user: User):Boolean{
        var passwordValid  = signupViewModel.checkPassword(user)
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