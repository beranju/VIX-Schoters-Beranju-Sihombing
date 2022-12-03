package com.nextgen.newsapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.nextgen.newsapp.data.local.database.News
import com.nextgen.newsapp.data.local.database.NewsDao
import com.nextgen.newsapp.data.local.database.NewsDatabase
import com.nextgen.newsapp.data.mediator.NewsRemoteMediator
import com.nextgen.newsapp.data.remote.api.ApiService
import com.nextgen.newsapp.data.remote.dto.HeadlineResponse
import com.nextgen.newsapp.data.remote.dto.SearchResponse
import com.nextgen.newsapp.helper.Async
import com.nextgen.newsapp.util.DateFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val PAGE_SIZE = 3

class NewsRepository(
    private val apiService: ApiService,
    private val database: NewsDatabase,
    private val newsDao: NewsDao,
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

    @OptIn(ExperimentalPagingApi::class)
    fun getLatestNews(): LiveData<PagingData<News>>{
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            remoteMediator = NewsRemoteMediator(database, apiService),
            pagingSourceFactory = {
//                NewsPagingSource(apiService)
                newsDao.getNews()
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

    fun getPopularNews(): LiveData<Async<HeadlineResponse>> = liveData {
        emit(Async.Loading)
        try {
            val response = apiService.getPopularNews(
                "berita",
                DateFormatter.yesterday(), DateFormatter.today(), "popularity"
            )
            if (response.isSuccessful){
                val responseBody = response.body()
                if (responseBody!= null){
                    emit(Async.Success(responseBody))
                }else{
                    emit(Async.Error("null data"))
                }
            }else{
                emit(Async.Error(response.message().toString()))
            }
        }catch (e:Exception){
            emit(Async.Error(e.message.toString()))
        }
    }

    fun getSavedNews(): LiveData<List<News>>{
        return database.newsDao().getSavedNews()
    }

    suspend fun setSavedNews(news: News, isSaved: Boolean){
        coroutineScope {
            launch {
                withContext(Dispatchers.IO){
                    news.isSaved = isSaved
                    newsDao.update(news)

                }
            }
        }
    }


}