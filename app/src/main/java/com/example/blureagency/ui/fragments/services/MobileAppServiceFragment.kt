package com.example.blureagency.ui.fragments.services

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.blureagency.R
import com.example.blureagency.ui.MainActivity


class MobileAppServiceFragment : Fragment(R.layout.fragment_mbile_app_service) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNav()
    }
    fun hideBottomNav(){
        (activity as MainActivity).hideNav()
    }

}