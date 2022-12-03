package com.nextgen.newsapp.data.remote.api

import com.nextgen.newsapp.BuildConfig
import com.nextgen.newsapp.BuildConfig.API_KEY
import com.nextgen.newsapp.data.local.database.News
import com.nextgen.newsapp.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiService {
    companion object{
        const val API_KEY = BuildConfig.API_KEY
    }

    @GET("top-headlines")
    @Headers("Authorization: apiKey $API_KEY", "User-Agent: request")
    suspend fun headlineNews(@Query("sources") sources: String): Response<HeadlineResponse>

    @GET("everything")
    @Headers("Authorization: apiKey $API_KEY", "User-Agent: request")
    suspend fun getSearchNews(@Query("q") query: String): Response<SearchResponse>


    @GET("top-headlines")
    @Headers("Authorization: apiKey $API_KEY", "User-Agent: request")
    suspend fun getLatestNews(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): LatestResponse

    @GET("top-headlines")
    @Headers("Authorization: apiKey $API_KEY", "User-Agent: request")
    suspend fun getLatestNewsCategory(
        @Query("country") country: String, @Query("category") category: String
    ): Response<HeadlineResponse>



}