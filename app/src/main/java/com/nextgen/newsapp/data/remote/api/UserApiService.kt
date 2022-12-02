package com.nextgen.newsapp.data.remote.api

import com.nextgen.newsapp.BuildConfig
import com.nextgen.newsapp.data.remote.dto.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UserApiService {
    companion object{
        const val API_KEY_GITHUB = BuildConfig.API_KEY_GITHUB
    }

    @GET("users/{login}")
    @Headers("Authorization: token $API_KEY_GITHUB", "User-Agent: request")
    suspend fun getGithubProfile(
        @Path("login") login: String
    ): Response<UserResponse>

}