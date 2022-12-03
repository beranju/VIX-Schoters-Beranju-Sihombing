package com.nextgen.newsapp.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nextgen.newsapp.R
import com.nextgen.newsapp.data.local.database.News
import com.nextgen.newsapp.data.remote.dto.ArticlesItem
import com.nextgen.newsapp.databinding.ItemSaveNewsBinding
import com.nextgen.newsapp.databinding.ItemSearchBinding
import com.nextgen.newsapp.ui.detail.DetailFragment
import com.nextgen.newsapp.util.DateFormatter
import java.util.*
import kotlin.collections.ArrayList

class SaveNewsAdapter(private val onSaved: (News)-> Unit): ListAdapter<News, SaveNewsAdapter.SearchViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSaveNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        if (data.isSaved){
            holder.binding.saveNews.setImageResource(R.drawable.ic_baseline_bookmark_24)
        }else{
            holder.binding.saveNews.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
        }
        holder.binding.saveNews.setOnClickListener {
            onSaved(data)
        }
    }


    class SearchViewHolder(val binding: ItemSaveNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: News) {
            binding.titleNews.text = data.title
            binding.sourceNews.text = data.sourceName
            binding.publishAt.text = DateFormatter.formatDate(data.publishedAt.toString(), TimeZone.getDefault().id)
            Glide.with(itemView.context)
                .load(data.urlToImage)
                .centerCrop()
                .into(binding.thumnail)
        }
    }

    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<News>(){
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem == newItem
            }

        }
    }
}