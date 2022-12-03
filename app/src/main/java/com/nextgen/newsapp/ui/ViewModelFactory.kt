package com.nextgen.newsapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nextgen.newsapp.di.Injection
import com.nextgen.newsapp.ui.detail.NewsDetailViewModel
import com.nextgen.newsapp.ui.home.HomeViewModel
import com.nextgen.newsapp.ui.news.NewsViewModel
import com.nextgen.newsapp.ui.news.SaveNewsViewModel
import com.nextgen.newsapp.ui.profile.ProfileViewModel
import com.nextgen.newsapp.ui.search.SearchViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(Injection.provideRepository(context)) as T
        }
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)){
            return SearchViewModel(Injection.provideRepository(context)) as T
        }
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)){
            return NewsViewModel(Injection.provideRepository(context)) as T
        }
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(Injection.provideUserRepository(context)) as T
        }
        if (modelClass.isAssignableFrom(SaveNewsViewModel::class.java)){
            return SaveNewsViewModel(Injection.provideRepository(context)) as T
        }
        if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java)){
            return NewsDetailViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}