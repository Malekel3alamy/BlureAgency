package com.example.blureagency.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.lifecycleScope
import com.example.blureagency.databinding.ActivitySplashBinding
import kotlinx.coroutines.launch
import java.io.IOException

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    val MODE_FILE_NAME = "mode.txt"
    val LANGUAGE_FILE_NAME = "lang.txt"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myPreferences = this@SplashActivity.getPreferences(Context.MODE_PRIVATE)

        lifecycleScope.launch {
            val status = read(MODE_FILE_NAME)

            Log.d("MODE_SWITCH_STATUS_Spalsh", status)

            if (status == "true") {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }


            val language = read(LANGUAGE_FILE_NAME)

            Log.d("MODE_SWITCH_STATUS_Spalsh", status)

            if (language == "ar") {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar"))

            } else {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
            }

        }


//        val animation :AnimationDrawable = binding.imageView.background as AnimationDrawable
//        animation.setEnterFadeDuration(1000)
//        animation.setExitFadeDuration(1000)
//        animation.start()


       Handler().postDelayed({
            val intent = Intent(this,StartActivity::class.java)
            startActivity(intent)
            finish()
        },3000L)


    }

    fun read(file_name:String):String{
        try {
            val content = this.openFileInput(file_name).bufferedReader().use { it.readText() }
            return content
        } catch (e: IOException) {
            e.printStackTrace()
            return "false"
        }
    }
}


/*   val   MODE_SWITCH_STATUS = myPreferences.getBoolean("mode_swith_status",false)
         val   DARK_MODE_STATUS =   myPreferences.getBoolean("dark_mode",false)


         myPreferences.registerOnSharedPreferenceChangeListener { sharedPreferences, s ->
             val   MODE_SWITCH_STATUS = sharedPreferences.getBoolean("mode_swith_status",false)
             Log.d("MODE_SWITCH_STATUS",MODE_SWITCH_STATUS.toString())
             if (MODE_SWITCH_STATUS){
                 AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

             }else{
                 AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
             }
         }
*/