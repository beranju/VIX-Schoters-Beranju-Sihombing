package com.nextgen.newsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nextgen.newsapp.data.repository.NewsRepository

class HomeViewModel(private val repository: NewsRepository) : ViewModel() {

    fun getHeadlineNews() = repository.getHeadlineNews()


}