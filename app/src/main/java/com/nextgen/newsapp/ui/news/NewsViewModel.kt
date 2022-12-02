package com.nextgen.newsapp.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nextgen.newsapp.data.repository.NewsRepository

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    fun getCategoryNews(category: String) = repository.getLatestNewsCategory(category)

}