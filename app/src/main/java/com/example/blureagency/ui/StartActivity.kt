package com.example.blureagency.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.blureagency.R
import com.example.blureagency.adapters.ViewPagerAdapter
import com.example.blureagency.databinding.ActivityStartActivtyBinding
import com.example.blureagency.models.ViewPagerServie

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
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.nextBTN.setOnClickListener {
            if (binding.startActivityViewPager.currentItem <6){
                binding.startActivityViewPager.currentItem = binding.startActivityViewPager.currentItem+1

            }else if(binding.startActivityViewPager.currentItem ==6){
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }

        if (binding.startActivityViewPager.currentItem == 6){
            binding.nextBTN.setOnClickListener {

            }

        }

    }


    private fun createViewPagerList():List<ViewPagerServie>{

        val item1 = ViewPagerServie(0,"android.resource://"+packageName+"/"+R.drawable.web5,"Web Development")
        val item2 = ViewPagerServie(1,"android.resource://"+packageName+"/"+R.drawable.android_intro_1,"Android Development")
        val item3 = ViewPagerServie(2,"android.resource://"+packageName+"/"+R.drawable.network_system,"Network Systems")
        val item4 = ViewPagerServie(3,"android.resource://"+packageName+"/"+R.drawable.security_system,"Security Systems")
        val item5 = ViewPagerServie(4,"android.resource://"+packageName+"/"+R.drawable.marketing5,"Marketing")
        val item6 = ViewPagerServie(5,"android.resource://"+packageName+"/"+R.drawable.video_edit,"Video Editing")
        val item7 = ViewPagerServie(6,"android.resource://"+packageName+"/"+R.drawable.general_finishes2,"General")
        val servicesList = listOf(item1,item2,item3,item4,item5,item6,item7)
        return servicesList
    }
}