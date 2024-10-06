package com.example.blureagency.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewMode @Inject constructor(
    val firebase_auth : FirebaseAuth
) : ViewModel(){


}