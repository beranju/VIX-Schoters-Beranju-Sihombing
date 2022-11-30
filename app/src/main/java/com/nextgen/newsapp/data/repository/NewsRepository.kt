package com.nextgen.newsapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.nextgen.newsapp.BuildConfig
import com.nextgen.newsapp.data.remote.api.ApiService
import com.nextgen.newsapp.data.remote.dto.HeadlineResponse
import com.nextgen.newsapp.data.remote.dto.SearchResponse
import com.nextgen.newsapp.helper.Async

class NewsRepository(
    private val apiService: ApiService
) {
    companion object{
        private val API_KEY = BuildConfig.API_KEY
    }

    fun getHeadlineNews(): LiveData<Async<HeadlineResponse>> = liveData {
        emit(Async.Loading)
        try {
            val response = apiService.headlineNews("id", API_KEY)
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
            val response = apiService.getSearchNews(query, API_KEY)
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

}