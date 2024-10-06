package com.example.blureagency.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blureagency.model.User
import com.example.movies.utils.Resources
import com.example.storeapp.utils.RegisterValidation
import com.example.storeapp.utils.validateEmail
import com.example.storeapp.utils.validatePassword
import com.example.storeapp.utils.validatePhoneNumber
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    val firebase_auth : FirebaseAuth ,
    val firestore :FirebaseFirestore
) : ViewModel(){



    private val _signupStatus = MutableSharedFlow<Resources<Unit>>()
    val signupStatus = _signupStatus.asSharedFlow()


 private val _emailValidation = MutableSharedFlow<RegisterValidation>()
val emailValidation = _emailValidation.asSharedFlow()

    private val _PasswordValidation  = MutableSharedFlow<RegisterValidation>()
            val passwordValidation = _PasswordValidation.asSharedFlow()



    
    fun isSignupFieldsNotEmpty(user:User):Boolean{

        if (user.userName.isEmpty()||user.phoneNumber.isEmpty()||user.email.isEmpty()||user.password.isEmpty()){
            return false
        }else{
            return true
        }

    }


    fun signupWithFirebase(email: String,password: String) =viewModelScope.launch {
            _signupStatus.emit(Resources.Loading())
        firebase_auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
            viewModelScope.launch {
                _signupStatus.emit(Resources.Success(Unit))
            }

        }.addOnFailureListener {
           viewModelScope.launch {
               _signupStatus.emit(Resources.Error(it.message.toString()))
           }
        }
    }

    fun checkEmail(user:User):Boolean {

           val emailValidation = validateEmail(user.email)
       return emailValidation
    }

    fun checkPassword(user:User):Boolean{
        val passwordValidation =  validatePassword(user.password)
        return passwordValidation
    }

    fun checkPhoneNumber(user:User):Boolean {

        val phoneNumberValidation = validatePhoneNumber(user.phoneNumber)
        return phoneNumberValidation
    }


    fun saveUserInfo(user: User)=viewModelScope.launch {

        firestore.collection("users").document(firebase_auth.uid.toString()).set(user)
            .addOnSuccessListener {
                Log.d("RegisterFragment"," succeeded  To Save ")
            }.addOnFailureListener {
                Log.d("RegisterFragment"," Failed To Save ")
            }
    }

}