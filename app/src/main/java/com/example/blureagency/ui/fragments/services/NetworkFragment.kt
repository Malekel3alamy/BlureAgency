package com.example.blureagency.ui.fragments.services

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.blureagency.R
import com.example.blureagency.databinding.FragmentNetworkBinding
import com.example.blureagency.ui.MainActivity

class NetworkFragment : Fragment(R.layout.fragment_network) {
lateinit var binding : FragmentNetworkBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNetworkBinding.bind(view)
        hideBottomNav()



    }

    fun hideBottomNav(){
        (activity as MainActivity).hideNav()
    }
}