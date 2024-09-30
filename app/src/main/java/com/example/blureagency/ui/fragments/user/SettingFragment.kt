package com.example.blureagency.ui.fragments.user

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.blureagency.R
import com.example.blureagency.databinding.FragmentSettingBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader


class SettingFragment : Fragment(R.layout.fragment_setting) {

    private lateinit var binding : FragmentSettingBinding
    val MODE_FILE_NAME = "mode.txt"
    val LANGUAGE_FILE_NAME = "lang.txt"



    private var LANGUAGE_SWITCH_STATUS =false
    private var ARABIC_STATUS = false






    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingBinding.bind(view)


        val editor =  requireActivity().getPreferences(Context.MODE_PRIVATE).edit()
        val myPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)

       val MODE_SWITCH_STATUS = myPreferences.getBoolean("mode_swith_status",false)
       val  DARK_MODE_STATUS =   myPreferences.getBoolean("dark_mode",false)




        if (MODE_SWITCH_STATUS ){
            binding.darkModeToggleBtn.isChecked =true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        }

            lifecycleScope.launch {

            val language = read(LANGUAGE_FILE_NAME)
            if (language == "ar"){
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar"))
                binding.arabicLanguageToggleBtn.isChecked = true
            }else if (language == "en"){
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
                binding.arabicLanguageToggleBtn.isChecked = false
            }
        }





        binding.darkModeToggleBtn.setOnCheckedChangeListener { _, checked ->

            if (checked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("mode_swith_status",true)
                editor.putBoolean("dark_mode",true)
                editor.apply()


                lifecycleScope.launch {
    write("true",MODE_FILE_NAME)
}




            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("mode_swith_status",false)
                editor.putBoolean("dark_mode",false)
                editor.apply()
                lifecycleScope.launch {
                    write("false",MODE_FILE_NAME)
                }


            }
        }



        binding.arabicLanguageToggleBtn.setOnCheckedChangeListener { _, checked ->

            if (checked){
                   AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar"))
                lifecycleScope.launch{

                    write("ar",LANGUAGE_FILE_NAME)
                }

            }else{
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
                lifecycleScope.launch {
                    write("en",LANGUAGE_FILE_NAME)
                }

            }
        }


    }

    private fun write(data :String,file_name:String){
        try {
           requireActivity().openFileOutput(file_name,Context.MODE_PRIVATE).use { fos ->
                fos.write(data.toByteArray())
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

 private   fun read(file_name:String):String{
        try {
            val content = requireActivity().openFileInput(file_name).bufferedReader().use { it.readText() }
            return content
        } catch (e: IOException) {
            e.printStackTrace()
            return "false"
        }
    }

}