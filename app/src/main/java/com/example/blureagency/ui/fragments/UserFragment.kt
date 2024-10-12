package com.example.blureagency.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.blureagency.R
import com.example.blureagency.databinding.FragmentSettingBinding
import com.example.blureagency.databinding.FragmentUserBinding
import com.example.blureagency.model.User
import com.example.blureagency.ui.fragments.auth.LoginActivity
import com.example.blureagency.ui.viewmodel.SignupViewModel
import com.example.movies.utils.Resources
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : Fragment(R.layout.fragment_user) {

    private lateinit var binding: FragmentUserBinding
    private val signupViewModel by viewModels<SignupViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBinding.bind(view)

        signupViewModel.getUserInfo()


        binding.userDetails.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_userInfoFragment2)
        }

        lifecycleScope.launch {
            signupViewModel.userInfo.collectLatest {
                when(it){
                    is Resources.Error -> {
                        Log.d("USER_NAME"," Failed to get user name from firebase")
                    }
                    is Resources.Loading -> {
                        Log.d("USER_NAME"," loading  to get user name from firebase")

                    }
                    is Resources.Success -> {
                       it.data?.let {
                           Log.d("USER_NAME",it.userName)
                          lifecycleScope.launch (Dispatchers.Main){
                              binding.userNameTv.text = it.userName
                          }
                       }
                    }
                }
            }
        }










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