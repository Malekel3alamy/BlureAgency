package com.example.blureagency.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.ActionBarOverlayLayout
import androidx.navigation.fragment.findNavController
import com.example.blureagency.R
import com.example.blureagency.databinding.ActivityStartActivtyBinding
import com.example.blureagency.databinding.FragmentHomeBinding
import com.example.blureagency.ui.MainActivity


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)


        binding.whatsUpCardView.setOnClickListener {


       /*    val package_manager = (activity as MainActivity).packageManager
            val uri = Uri.parse("smsto"+"+2001093107879")
            val intent = Intent(Intent.ACTION_SENDTO,uri)
            intent.`package`="com.whatsapp"
            if (intent.resolveActivity(package_manager) != null){
                startActivity(intent)
            }else{
                Toast.makeText(requireContext(),"Whats app is Not Installed ",Toast.LENGTH_SHORT).show()
            }*/

            if (isPackageInstalled(requireContext(),"com.whatsapp") ||
                isPackageInstalled(requireContext(),"com.whatsapp.w4b")){

                val intent  =  Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+20"+"1281504887"+"&text="+""))
                startActivity(intent)
            }else{
                Toast.makeText(requireContext(),"Whats app is Not Installed ",Toast.LENGTH_SHORT).show()
            }

        }

        binding.offerCardView.setOnClickListener {
             findNavController().navigate(R.id.action_homeFragment_to_offersFragment2)
        }
    }
    fun isPackageInstalled(context: Context, packagename: String?): Boolean {
        var result = true
        try {
            // is the application installed?
            context.packageManager.getPackageInfo(packagename!!, PackageManager.GET_ACTIVITIES)
        } catch (e: PackageManager.NameNotFoundException) {
            result = false

        }
        return result
    }

}