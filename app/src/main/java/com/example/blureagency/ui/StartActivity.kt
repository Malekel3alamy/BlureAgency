package com.example.blureagency.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.blureagency.R
import com.example.blureagency.adapters.ViewPagerAdapter
import com.example.blureagency.databinding.ActivityStartActivtyBinding
import com.example.blureagency.models.ViewPagerServie
import com.example.blureagency.ui.fragments.auth.LoginActivity

class StartActivity : AppCompatActivity() {
    private lateinit var binding:ActivityStartActivtyBinding
    private lateinit var viewPagerAdapter:ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewPagerAdapter = ViewPagerAdapter(createViewPagerList())
        binding.startActivityViewPager.adapter = viewPagerAdapter
        binding.wormDotsIndicator.attachTo(binding.startActivityViewPager)


        binding.SkipBTN.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.nextBTN.setOnClickListener {
            if (binding.startActivityViewPager.currentItem <1){
                binding.startActivityViewPager.currentItem = binding.startActivityViewPager.currentItem+1

            }else if(binding.startActivityViewPager.currentItem ==1){
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }

        if (binding.startActivityViewPager.currentItem == 1){
            binding.nextBTN.setOnClickListener {

            }

        }

    }


    private fun createViewPagerList():List<ViewPagerServie>{

        val item1 = ViewPagerServie(0,"android.resource://"+packageName+"/"+R.drawable.first_screen,
            resources.getString(R.string.fist_intro_text)
                    )
        val item2 = ViewPagerServie(1,"android.resource://"+packageName+"/"+R.drawable.android_intro_1,
            resources.getString(R.string.second_intro_text))

        val servicesList = listOf(item1,item2)
        return servicesList
    }
}