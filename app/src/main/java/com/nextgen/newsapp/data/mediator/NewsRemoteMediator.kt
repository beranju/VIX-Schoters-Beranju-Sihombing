package com.nextgen.newsapp.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.nextgen.newsapp.data.local.database.News
import com.nextgen.newsapp.data.local.database.NewsDatabase
import com.nextgen.newsapp.data.local.database.RemoteKeys
import com.nextgen.newsapp.data.remote.api.ApiService

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val database: NewsDatabase,
    private val apiService: ApiService
): RemoteMediator<Int, News>() {

    private companion object{
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, News>
    ): MediatorResult {

        val page = when(loadType){
            LoadType.REFRESH ->{
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND ->{
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND ->{
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached =  remoteKeys != null)
                nextKey

            }
        }

        try {
            val responseData = apiService.getLatestNews("id", page, state.config.pageSize).articles!!
            val listNews = ArrayList<News>()
            responseData.forEach { data->
                val news = News(
                    data.title.toString(), data.publishedAt, data.author, data.urlToImage, data.description, data.source!!.name, data.url, false
                )
                listNews.add(news)
            }

            val endOfPaginationReached = responseData.isEmpty()

            database.withTransaction{
                if (loadType == LoadType.REFRESH){
                    database.remoteKeysDao().deleteRemoteKeys()
                    database.newsDao().deleteAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = listNews.map {
                    RemoteKeys(title = it.title, prevKey = prevKey,  nextKey = nextKey)
                }
                database.remoteKeysDao().insertAll(keys)
                database.newsDao().insert(listNews)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (e: Exception){
            return MediatorResult.Error(e)
        }

    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, News>): RemoteKeys?{
        return state.pages.lastOrNull{ it.data.isNotEmpty()}?.data?.lastOrNull()?.let { data->
            database.remoteKeysDao().getRemoteKeysTitle(data.title)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, News>): RemoteKeys?{
        return state.pages.firstOrNull{it.data.isNotEmpty()}?.data?.firstOrNull()?.let { data->
            database.remoteKeysDao().getRemoteKeysTitle(data.title)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, News>): RemoteKeys?{
        return state.anchorPosition?.let { position->
            state.closestPageToPosition(position)?.let { title->
                database.remoteKeysDao().getRemoteKeysTitle(title.toString())
            }
        }
    }
}