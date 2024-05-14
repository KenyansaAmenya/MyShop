package com.example.myshop.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.myshop.Adapter.SliderAdapter.SliderViewHolder
import com.example.myshop.Domain.SliderItems
import com.example.myshop.R




class SliderAdapter(
    private val sliderItems: ArrayList<SliderItems>,
    private val viewPager2: ViewPager2
) : RecyclerView.Adapter<SliderViewHolder>() {
    private var context: Context? = null
    private val runnable = Runnable {
        sliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        context = parent.context
        return SliderViewHolder(
            LayoutInflater.from(context).inflate(R.layout.slide_item_container, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.setImage(sliderItems[position])
        if (position == sliderItems.size - 2) {
            viewPager2.post(runnable)
        }
    }


    override fun getItemCount(): Int {
        return sliderItems.size
    }

    inner class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageSlide)
        fun setImage(sliderItems: SliderItems) {
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transform(CenterCrop())
            Glide.with(context!!)
                .load(sliderItems.url)
                .apply(requestOptions)
                .into(imageView)
        }
    }
}
