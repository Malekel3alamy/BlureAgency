package com.example.blureagency.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.blureagency.R
import com.example.blureagency.databinding.FragmentSettingBinding
import com.example.blureagency.databinding.FragmentUserBinding
import com.example.blureagency.ui.fragments.auth.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth


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
        binding.signOutCardView.setOnClickListener {
           signOutAndStartSignInActivity()
        }
    }

    private fun signOutAndStartSignInActivity() {
        FirebaseAuth.getInstance().signOut()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        googleSignInClient.signOut().addOnCompleteListener(requireActivity()) {
            // Optional: Update UI or show a message to the user
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }
    }
}