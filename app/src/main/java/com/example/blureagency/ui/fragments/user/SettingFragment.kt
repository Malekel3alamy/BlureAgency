package com.example.blureagency.ui.fragments.user

import android.app.Application
import android.app.LocaleManager
import android.app.UiModeManager
import android.app.backup.SharedPreferencesBackupHelper
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.navigation.fragment.findNavController
import com.example.blureagency.R
import com.example.blureagency.databinding.FragmentSettingBinding


class SettingFragment : Fragment(R.layout.fragment_setting) {

    private lateinit var binding : FragmentSettingBinding
    var dark_mode_status =false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingBinding.bind(view)

        val language = AppCompatDelegate.getApplicationLocales() .toLanguageTags()
        val mode = AppCompatDelegate.getDefaultNightMode().equals(AppCompatDelegate.MODE_NIGHT_YES)

        if (language=="ar"){
            binding.arabicLanguageToggleBtn.isChecked = true

            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar"))
        }else{
            binding.arabicLanguageToggleBtn.isChecked = false
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))

        }
        if (mode){
            binding.darkModeToggleBtn.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            dark_mode_status =true
        }else{
            binding.darkModeToggleBtn.isChecked = false
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            dark_mode_status =false

        }

        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE) ?: return
        val mode_status = sharedPref.getBoolean(getString(R.string.saved_high_score_key), false)




        if (mode_status){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }


        binding.darkModeToggleBtn.setOnCheckedChangeListener { _, checked ->

            if (checked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                dark_mode_status =true
                val i = PreferenceManager.getDefaultSharedPreferences(requireContext())
                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return@setOnCheckedChangeListener
                with (sharedPref.edit()) {

                    putBoolean(getString(R.string.saved_high_score_key), dark_mode_status)
                    apply()
                }

            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                dark_mode_status =false
            }
        }

        binding.arabicLanguageToggleBtn.setOnCheckedChangeListener { _, checked ->

            if (checked){
                   AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar"))
            }else{
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
            }
        }


    }

    override fun onResume() {
        super.onResume()


    }

    override fun onStop() {
        super.onStop()
       // findNavController().navigateUp()
    }
}