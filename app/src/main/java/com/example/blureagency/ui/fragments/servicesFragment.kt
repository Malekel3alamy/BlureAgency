package com.example.blureagency.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.blureagency.R
import com.example.blureagency.databinding.FragmentServicesBinding
import com.example.blureagency.ui.MainActivity


class servicesFragment : Fragment(R.layout.fragment_services) {
private lateinit var binding: FragmentServicesBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentServicesBinding.bind(view)



        binding.marketingService.setOnClickListener {
            findNavController().navigate(R.id.action_servicesFragment_to_marketingFragment)
        }

        binding.WebAppService.setOnClickListener {
            findNavController().navigate(R.id.action_servicesFragment_to_webFragment)
        }

        binding.networkService.setOnClickListener {
            findNavController().navigate(R.id.action_servicesFragment_to_networkFragment)
        }

        binding.Security.setOnClickListener {
            findNavController().navigate(R.id.action_servicesFragment_to_securitySystemsFragment)
        }

        binding.GeneralContracting.setOnClickListener {
            findNavController().navigate(R.id.action_servicesFragment_to_generalFragment)
        }

        binding.videoEditService.setOnClickListener {
            findNavController().navigate(R.id.action_servicesFragment_to_videoEditingFragment)
        }


        binding.androidService.setOnClickListener {
            findNavController().navigate(R.id.action_servicesFragment_to_mobileAppServiceFragment2)
        }



    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showNav()
    }

}