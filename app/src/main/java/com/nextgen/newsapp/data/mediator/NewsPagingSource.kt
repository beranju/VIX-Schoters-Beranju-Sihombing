package com.nextgen.newsapp.data.mediator

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nextgen.newsapp.data.local.database.News
import com.nextgen.newsapp.data.remote.api.ApiService
import com.nextgen.newsapp.data.remote.dto.ArticlesItem
import com.nextgen.newsapp.data.remote.dto.HeadlineResponse

class NewsPagingSource(private val apiService: ApiService): PagingSource<Int, News>() {

    companion object{
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getLatestNews("id", INITIAL_PAGE_INDEX, params.loadSize).articles!!
//            val listNews = ArrayList<News>()
//            responseData.forEach { data->
//                val news = News(
//                    data.title.toString(), data.publishedAt, data.author, data.url, data.description, data.source!!.name, data.url, false
//                )
//                listNews.add(news)
//            }


            LoadResult.Page(
                data = responseData ,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else position + 1
            )
        }catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition?.let {anchorPosition->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)

        }
    }

}