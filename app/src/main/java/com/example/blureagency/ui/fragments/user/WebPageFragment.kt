package com.example.blureagency.ui.fragments.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.blureagency.R
import com.example.blureagency.databinding.FragmentWebPageBinding
import com.example.blureagency.ui.MainActivity


class WebPageFragment : Fragment(R.layout.fragment_web_page) {
    private lateinit var binding : FragmentWebPageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentWebPageBinding.bind(view)
        hideBottomNav()

        binding.webView.apply {

            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
           loadUrl("https://blore-f4515.web.app/")

        }
    }

    fun hideBottomNav(){
        (activity as MainActivity).hideNav()
    }

}