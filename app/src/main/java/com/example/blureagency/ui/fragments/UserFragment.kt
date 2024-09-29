package com.example.blureagency.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.blureagency.R
import com.example.blureagency.databinding.FragmentSettingBinding
import com.example.blureagency.databinding.FragmentUserBinding


class UserFragment : Fragment(R.layout.fragment_user) {

    private lateinit var binding: FragmentUserBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBinding.bind(view)

        binding.about.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_aboutFragment)
        }

        binding.settings.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_settingFragment)
        }
    }
}