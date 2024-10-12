package com.example.blureagency.ui.fragments.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.blureagency.R
import com.example.blureagency.databinding.FragmentGeneralBinding
import com.example.blureagency.ui.MainActivity


class GeneralFragment : Fragment(R.layout.fragment_general) {
     private lateinit var binding: FragmentGeneralBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         binding = FragmentGeneralBinding.bind(view)
        hideBottomNav()



    }
    fun hideBottomNav(){
        (activity as MainActivity).hideNav()
    }
}