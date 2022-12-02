package com.nextgen.newsapp.data.mediator

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nextgen.newsapp.data.remote.api.ApiService
import com.nextgen.newsapp.data.remote.dto.ArticlesItem
import com.nextgen.newsapp.data.remote.dto.HeadlineResponse

class NewsPagingSource(private val apiService: ApiService): PagingSource<Int, ArticlesItem>() {

    companion object{
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getLatestNews("id", INITIAL_PAGE_INDEX, params.loadSize).articles!!


            LoadResult.Page(
                data = responseData ,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else position + 1
            )
        }catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        return state.anchorPosition?.let {anchorPosition->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)

        }
    }

}