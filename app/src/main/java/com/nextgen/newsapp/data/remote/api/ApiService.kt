package com.nextgen.newsapp.data.remote.api

import com.nextgen.newsapp.data.remote.dto.HeadlineResponse
import com.nextgen.newsapp.data.remote.dto.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiService {

    @GET("top-headlines")
    suspend fun headlineNews(@Query("country") country: String, @Query("apiKey") apiKey: String): Response<HeadlineResponse>

    @GET("everything")
    suspend fun getSearchNews(@Query("q") query: String, @Query("apiKey") apiKey: String): Response<SearchResponse>

}