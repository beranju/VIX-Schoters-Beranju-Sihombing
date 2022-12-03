package com.nextgen.newsapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.newsapp.data.local.database.News
import com.nextgen.newsapp.data.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsDetailViewModel(private val repository: NewsRepository): ViewModel() {

    fun getSavedNews() = repository.getSavedNews()


    fun saveNews(news: News){
        viewModelScope.launch {
            repository.setSavedNews(news, true)
        }
    }

    fun unSaveNews(news: News){
        viewModelScope.launch {
            repository.setSavedNews(news, false)
        }
    }
}