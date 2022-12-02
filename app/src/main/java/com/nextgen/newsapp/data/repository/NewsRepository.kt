package com.nextgen.newsapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.nextgen.newsapp.BuildConfig
import com.nextgen.newsapp.data.mediator.NewsPagingSource
import com.nextgen.newsapp.data.remote.api.ApiService
import com.nextgen.newsapp.data.remote.dto.ArticlesItem
import com.nextgen.newsapp.data.remote.dto.HeadlineResponse
import com.nextgen.newsapp.data.remote.dto.SearchResponse
import com.nextgen.newsapp.data.remote.dto.SourceResponse
import com.nextgen.newsapp.helper.Async

const val PAGE_SIZE = 5

class NewsRepository(
    private val apiService: ApiService
) {



    fun getHeadlineNews(): LiveData<Async<HeadlineResponse>> = liveData {
        emit(Async.Loading)
        try {
            val response = apiService.headlineNews("bbc-news")
            if (response.isSuccessful){
                val responseBody = response.body()!!
                emit(Async.Success(responseBody))
            }else{
                val error = response.message()
                emit(Async.Error(error))
            }
        }catch (e: Exception){
            emit(Async.Error(e.message.toString()))
        }
    }

    fun getSearchNews(query: String): LiveData<Async<SearchResponse>> = liveData {
        emit(Async.Loading)
        try {
            val response = apiService.getSearchNews(query)
            if (response.isSuccessful){
                val responseBody = response.body()
                if (responseBody != null){
                    emit(Async.Success(responseBody))
                }
            }else{
                emit(Async.Error(response.message().toString()))
            }
        }catch (e: Exception){
            emit(Async.Error(e.message.toString()))
        }
    }

    fun getLatestNews(): LiveData<PagingData<ArticlesItem>>{
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                NewsPagingSource(apiService)
            }
        ).liveData
    }

    fun getLatestNewsCategory(category: String): LiveData<Async<HeadlineResponse>> = liveData {
        emit(Async.Loading)
        try {
            val response = apiService.getLatestNewsCategory("id", category)
            if (response.isSuccessful){
                val responseBody = response.body()
                if (responseBody!= null){
                    emit(Async.Success(responseBody))
                }else{
                    emit(Async.Error("null responseBody"))
                }
            }else{
                emit(Async.Error(response.message()))
            }
        }catch (e: Exception){
            emit(Async.Error(e.message.toString()))
        }
    }

}