package com.android.animeAndroidApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.animeAndroidApp.models.Pictures
import com.bumptech.glide.Glide

class ImageRvAdapter(val pictures: List<Map<String, String>>): RecyclerView.Adapter<ImageRvAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemImage: ImageView = itemView.findViewById(R.id.item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide
            .with(holder.itemView.context)
            .load(pictures[position]["small"])
            .into(holder.itemImage)
    }

    override fun getItemCount(): Int {
        return pictures.size
    }
}