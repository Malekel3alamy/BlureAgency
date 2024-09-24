package com.example.blureagency.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blureagency.R
import com.example.blureagency.models.ViewPagerServie


 class ViewPagerAdapter (private val list:List<ViewPagerServie>) :RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>(){
    class MyViewHolder(val view:View):RecyclerView.ViewHolder(view) {

        val image = view.findViewById<ImageView>(R.id.viewPagerImageView)
        val discription = view.findViewById<TextView>(R.id.viewPagerItemDescription)


    }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
         return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_pager2_item,parent,false))
     }

     override fun getItemCount(): Int {
         return list.size
     }

     override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

         val item = list[position]
         Glide.with(holder.itemView).load(item.url).into(holder.image)
         holder.discription.text = item.description
     }
 }