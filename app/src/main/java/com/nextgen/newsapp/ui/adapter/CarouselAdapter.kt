package com.nextgen.newsapp.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nextgen.newsapp.R
import com.nextgen.newsapp.data.remote.dto.ArticlesItem
import com.nextgen.newsapp.databinding.ItemCorouselMainBinding
import com.nextgen.newsapp.ui.detail.DetailFragment

class CarouselAdapter(private val listNews: ArrayList<ArticlesItem>): RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding = ItemCorouselMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val data = listNews[position]
        holder.bind(data)
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(DetailFragment.URL, data.url)
            it.findNavController().navigate(R.id.action_navigation_home_to_detailFragment, bundle)
        }
    }

    override fun getItemCount(): Int = listNews.size


    class CarouselViewHolder(val binding: ItemCorouselMainBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ArticlesItem) {
            binding.titleNews.text = data.title
            binding.sourceNews.text = data.source?.name
            Glide.with(itemView.context)
                .load(data.urlToImage)
                .centerCrop()
                .into(binding.thumnail)
        }
    }
}