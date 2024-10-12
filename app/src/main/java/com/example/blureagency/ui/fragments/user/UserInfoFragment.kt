package com.example.blureagency.ui.fragments.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.blureagency.R
import com.example.blureagency.databinding.FragmentUserInfoBinding
import com.example.blureagency.ui.MainActivity
import com.example.blureagency.ui.viewmodel.SignupViewModel
import com.example.movies.utils.Resources
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserInfoFragment : Fragment(R.layout.fragment_user_info) {
private lateinit var binding:FragmentUserInfoBinding

private val signupViewModel by viewModels<SignupViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserInfoBinding.bind(view)

        hideBottomNav()
        signupViewModel.getUserInfo()

        lifecycleScope.launch {
            signupViewModel.userInfo.collectLatest {
                when(it){
                    is Resources.Error -> {
                         hidePR()
                        Toast.makeText(requireContext()," Failed to get user info",Toast.LENGTH_SHORT).show()
                    }
                    is Resources.Loading -> {
                         showPR()
                    }
                    is Resources.Success -> {
                        hidePR()
                        it.data?.let {
                            binding.userInfoUserNameEt.setText(it.userName)
                            binding.userInfoPhoneNumberEt.setText(it.phoneNumber)
                            binding.userInfoEmailEt.setText(it.email)
                        }
                    }
                }
            }
        }



    }
    fun hideBottomNav(){
        (activity as MainActivity).hideNav()
    }
    private fun showPR() {
        binding.apply {
            userInfoPr.visibility = View.VISIBLE
            userInfoEditBtn.visibility = View.INVISIBLE
            userInfoUserNameEt.visibility = View.INVISIBLE
            userInfoPhoneNumberEt.visibility = View.INVISIBLE
            userInfoEmailEt.visibility = View.INVISIBLE
        }
    }

    private fun hidePR() {
        binding.apply {
            userInfoPr.visibility = View.INVISIBLE
            userInfoEditBtn.visibility = View.VISIBLE
            userInfoUserNameEt.visibility = View.VISIBLE
            userInfoPhoneNumberEt.visibility = View.VISIBLE
            userInfoEmailEt.visibility = View.VISIBLE
        }
    }
}