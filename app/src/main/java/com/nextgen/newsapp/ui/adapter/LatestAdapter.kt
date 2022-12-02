package com.nextgen.newsapp.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nextgen.newsapp.R
import com.nextgen.newsapp.data.remote.dto.ArticlesItem
import com.nextgen.newsapp.databinding.ItemLatestnewsBinding
import com.nextgen.newsapp.databinding.ItemSearchBinding
import com.nextgen.newsapp.ui.detail.DetailFragment
import com.nextgen.newsapp.ui.detail.NewsDetailFragment

class LatestAdapter(): PagingDataAdapter<ArticlesItem, LatestAdapter.SearchViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemLatestnewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null){
            holder.bind(data)
        }
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable(NewsDetailFragment.ARTICLE_DATA, data)
            it.findNavController().navigate(R.id.action_navigation_home_to_detailNewsFragment, bundle)
        }
    }


    class SearchViewHolder(val binding: ItemLatestnewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ArticlesItem) {
            binding.titleNews.text = data.title
            binding.sourceNews.text = data.source!!.name
            binding.publishAt.text = data.publishedAt
            Glide.with(itemView.context)
                .load(data.urlToImage)
                .centerCrop()
                .into(binding.thumnail)

        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}