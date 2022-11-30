package com.nextgen.newsapp.ui.search

import androidx.lifecycle.ViewModel
import com.nextgen.newsapp.data.repository.NewsRepository

class SearchViewModel(private val repository: NewsRepository): ViewModel() {

    fun getSearchNews(query: String) = repository.getSearchNews(query)
}