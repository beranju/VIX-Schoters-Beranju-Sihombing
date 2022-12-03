package com.nextgen.newsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nextgen.newsapp.data.local.database.News
import com.nextgen.newsapp.data.repository.NewsRepository

class HomeViewModel(private val repository: NewsRepository) : ViewModel() {

    val news: LiveData<PagingData<News>> = repository.getLatestNews().cachedIn(viewModelScope)

    fun getHeadlineNews() = repository.getHeadlineNews()

    fun getPopularNews() = repository.getPopularNews()



}