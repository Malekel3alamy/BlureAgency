package com.example.blureagency.ui.viewmodel

import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blureagency.R
import com.example.blureagency.model.User
import com.example.movies.utils.Resources
import com.example.storeapp.utils.RegisterValidation
import com.example.storeapp.utils.validateEmail
import com.example.storeapp.utils.validatePassword
import com.example.storeapp.utils.validatePhoneNumber
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
   private val firebase_auth : FirebaseAuth ,
   private val firestore :FirebaseFirestore

) : ViewModel(){



    private val _signupStatus = MutableSharedFlow<Resources<Unit>>()
    val signupStatus = _signupStatus.asSharedFlow()


 private val _emailValidation = MutableSharedFlow<RegisterValidation>()
val emailValidation = _emailValidation.asSharedFlow()

    private val _PasswordValidation  = MutableSharedFlow<RegisterValidation>()
            val passwordValidation = _PasswordValidation.asSharedFlow()


    private val _emailVerification  = MutableSharedFlow<Resources<Unit>>()
    val emailVerification = _emailVerification.asSharedFlow()


    
    fun isSignupFieldsNotEmpty(user:User):Boolean{

        if (user.userName.isEmpty()||user.phoneNumber.isEmpty()||user.email.isEmpty()||user.password.isEmpty()){
            return false
        }else{
            return true
        }

    }


    fun signupWithFirebase(view: View, user:User) =viewModelScope.launch {
            _signupStatus.emit(Resources.Loading())
        firebase_auth.createUserWithEmailAndPassword(user.email,user.password).addOnSuccessListener {
            viewModelScope.launch {
                _signupStatus.emit(Resources.Success(Unit))
                firebase_auth.currentUser?.sendEmailVerification()?.addOnSuccessListener {
                    Snackbar.make(view,"email verification sent",Snackbar.LENGTH_INDEFINITE).show()
                    Log.d("Verification_email","Sent")


                    val verification = firebase_auth.currentUser?.isEmailVerified ?:false
                    setEmailVerification(verification)


                }?.addOnFailureListener {
                    Log.d("Verification_email","Failed To  Send")
                }


               /* firebase_auth.sendSignInLinkToEmail(user.email, actionCodeSettings{
                    // URL you want to redirect back to. The domain (www.example.com) for this
                    // URL must be whitelisted in the Firebase Console.
                    url = "https://www.blore_eg.com/finishSignUp?cartId=1234"
                    // This must be true
                    handleCodeInApp = true
                    setIOSBundleId("com.example.ios")
                    setAndroidPackageName(
                        "com.android.chrome",
                        true, // installIfNotAvailable
                        "12", // minimumVersion
                    )
                }).addOnSuccessListener {
                    Log.d("Email_Status", "Email sent.")
                }*/
                saveUserInfo(user)
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


   private fun saveUserInfo(user: User)=viewModelScope.launch {

        firestore.collection("users").document(firebase_auth.currentUser!!.uid).set(user)
            .addOnSuccessListener {
                Log.d("RegisterFragment"," succeeded  To Save ")
            }.addOnFailureListener {
                Log.d("RegisterFragment"," Failed To Save ")
            }
    }

    private fun setEmailVerification(verification:Boolean)  =viewModelScope.launch {
        if (verification){
            _emailVerification.emit(Resources.Success(Unit))
        }else{
            _emailVerification.emit(Resources.Error("Email Not Verified"))
        }
    }




}