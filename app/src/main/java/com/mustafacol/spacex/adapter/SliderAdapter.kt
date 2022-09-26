package com.mustafacol.spacex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import coil.load
import com.mustafacol.spacex.R
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(private val imageList: List<String?>) :
    SliderViewAdapter<SliderAdapter.SliderViewHolder>() {

    override fun getCount(): Int = imageList.size

    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.slider_item, parent, false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
        imageList[position]?.let { viewHolder.bindView(it) }
    }

    class SliderViewHolder(itemView: View) : ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.myimage)
        fun bindView(url: String) {
            imageView.load(url)
        }
    }

}
