package com.nextgen.newsapp.di

import android.content.Context
import com.nextgen.newsapp.data.remote.api.ApiConfig
import com.nextgen.newsapp.data.remote.api.ApiService
import com.nextgen.newsapp.data.repository.NewsRepository

object Injection {
    fun provideRepository(context: Context): NewsRepository{
        val apiService = ApiConfig.getApiService()
        return NewsRepository(apiService)
    }
}