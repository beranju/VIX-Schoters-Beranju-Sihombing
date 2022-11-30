package com.nextgen.newsapp.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nextgen.newsapp.R
import com.nextgen.newsapp.data.remote.dto.ArticlesItem
import com.nextgen.newsapp.databinding.ItemSearchBinding
import com.nextgen.newsapp.ui.detail.DetailFragment

class SearchAdapter(private val listNews: ArrayList<ArticlesItem>): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val data = listNews[position]
        holder.bind(data)
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(DetailFragment.URL, data.url)
            it.findNavController().navigate(R.id.action_searchFragment_to_detailFragment, bundle)
        }
    }

    override fun getItemCount(): Int = listNews.size


    class SearchViewHolder(val binding: ItemSearchBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ArticlesItem) {
            binding.titleNews.text = data.title
            binding.description.text = data.description
            binding.sourceNews.text = data.source!!.name
            binding.publishAt.text = data.publishedAt
            Glide.with(itemView.context)
                .load(data.urlToImage)
                .centerCrop()
                .into(binding.thumnail)

        }
    }
}