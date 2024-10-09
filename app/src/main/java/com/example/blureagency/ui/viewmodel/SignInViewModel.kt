package com.example.blureagency.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.utils.Resources
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
   private val firebase_auth : FirebaseAuth ,
   private val firestore : FirebaseFirestore
):ViewModel() {

    private val _signInStatus = MutableSharedFlow<Resources<Unit>>()
    val signInStatus = _signInStatus.asSharedFlow()


    private val _resetPassword= MutableSharedFlow<Resources<String>>()
    val resetPassword = _resetPassword.asSharedFlow()

 fun signInWithEmailAndPassword(email:String,password:String)=viewModelScope.launch {
     _signInStatus.emit(Resources.Loading())

         firebase_auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
           if (firebase_auth.currentUser!!.isEmailVerified){
               viewModelScope.launch {
                   _signInStatus.emit(Resources.Success(Unit))
               }
           }else{
               viewModelScope.launch {
                   _signInStatus.emit(Resources.Error("email not verified"))
               }
           }
         }.addOnFailureListener {
            viewModelScope.launch {
                _signInStatus.emit(Resources.Error("failed to log in"))
            }
         }


 }

    fun verifyEmailAndPasswordIsEmpty(email: String,password: String):Boolean{
        if (email.isEmpty()||password.isEmpty()){
             return false
        }else{
            return true
        }
    }


    fun resetPassword(email : String){

        viewModelScope.launch {
            firebase_auth.sendPasswordResetEmail(email).addOnSuccessListener {

            }.addOnFailureListener {
                viewModelScope.launch {
                    _resetPassword.emit(Resources.Error(" Failed To send Reset Password Email "))
                }
            }.addOnSuccessListener {
                viewModelScope.launch {
                    _resetPassword.emit(Resources.Success(email))
                }
            }
        }
    }



    }