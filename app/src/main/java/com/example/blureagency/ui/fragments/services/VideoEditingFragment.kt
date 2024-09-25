package com.example.blureagency.ui.fragments.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.blureagency.R
import com.example.blureagency.ui.MainActivity


class VideoEditingFragment : Fragment(R.layout.fragment_video_editing) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNav()


    }


    fun hideBottomNav(){
        (activity as MainActivity).hideNav()
    }
}