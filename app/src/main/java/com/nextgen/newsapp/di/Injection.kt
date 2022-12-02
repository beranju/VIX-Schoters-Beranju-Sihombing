package com.nextgen.newsapp.di

import android.content.Context
import com.nextgen.newsapp.data.local.database.NewsDatabase
import com.nextgen.newsapp.data.remote.api.ApiConfig
import com.nextgen.newsapp.data.remote.api.ApiService
import com.nextgen.newsapp.data.repository.NewsRepository
import com.nextgen.newsapp.data.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): NewsRepository{
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getDatabase(context)
        val newsDao = database.newsDao()
        return NewsRepository(apiService, database, newsDao)
    }
    fun provideUserRepository(context: Context): UserRepository{
        val apiService = ApiConfig.getUserApiService()
        return UserRepository(apiService)
    }
}