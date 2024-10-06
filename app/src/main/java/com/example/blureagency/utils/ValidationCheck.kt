package com.example.storeapp.utils

import android.util.Patterns

fun validateEmail (email:String) : Boolean{


    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        return false
    }
    return true
}

fun validatePhoneNumber(phoneNumber:String) : Boolean{

    if(phoneNumber.length <10){
        return false
    }
    return true
}

fun validatePassword(password:String) :Boolean{

    if(password.length <6){
        return false
    }
    return true




//    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//        return return RegisterValidation.Failed("this pattern is not accepted ")
//    }
//    return RegisterValidation.Success()
//}
//
//fun validatePassword(password:String) :RegisterValidation{
//
//    if(password.length <6){
//        return RegisterValidation.Failed(" Password can not be less than 6 characters ")
//    }
//    return RegisterValidation.Success()
}
