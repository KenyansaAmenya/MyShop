package com.example.myshop.Activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.myshop.Adapter.SliderAdapter
import com.example.myshop.Domain.SliderItems
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener


class MainActivity : BaseActivity() {
     private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Remove () after layoutInflater
        setContentView(binding.root)


        initBanner()

    }

    private fun initBanner() {
        val myRef: DatabaseReference = database.getReference("Banner")
        binding.progressBarBanner.visibility (View.VISIBLE)
        val items = ArrayList<SliderItems>()
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (issue in snapshot.children) {
                        items.add(issue.getValue(SliderItems::class.java)!!)
                    }
                    banners(items)
                    binding.progressBarBanner.visibility(View.GONE)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event if needed
            }
        })
    }

    private fun banners(items: ArrayList<SliderItems>) {

        binding.viewpagerSlider.adapter (SliderAdapter(items, binding.viewpagerSlider))
        binding.viewpagerSlider.clipToPadding (false)
        binding.viewpagerSlider.clipChildren (false)
        binding.viewpagerSlider.offscreenPageLimit (3)
        binding.viewpagerSlider.getChildAt(0).overScrollMode (RecyclerView.OVER_SCROLL_NEVER)

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))

        binding.viewpagerSlider.setPageTransformer(compositePageTransformer)
    }
}