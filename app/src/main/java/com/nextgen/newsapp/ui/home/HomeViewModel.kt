package com.nextgen.newsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nextgen.newsapp.data.remote.dto.ArticlesItem
import com.nextgen.newsapp.data.repository.NewsRepository

class HomeViewModel(private val repository: NewsRepository) : ViewModel() {

    val news: LiveData<PagingData<ArticlesItem>> = repository.getLatestNews().cachedIn(viewModelScope)

    fun getHeadlineNews() = repository.getHeadlineNews()



}