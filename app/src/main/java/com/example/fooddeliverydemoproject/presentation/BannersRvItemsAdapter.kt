package com.example.fooddeliverydemoproject.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fooddeliverydemoproject.databinding.BannersRvItemBinding

class BannersRvItemsAdapter: RecyclerView.Adapter<BannersRvItemsAdapter.ViewHolder>() {

    private var images: List<Int> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BannersRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun setImageList(images: List<Int>) {
        this.images = images
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        holder.image.setImageResource(image)

    }

    class ViewHolder(binding: BannersRvItemBinding): RecyclerView.ViewHolder(binding.root) {
        val image = binding.imageView
    }
}