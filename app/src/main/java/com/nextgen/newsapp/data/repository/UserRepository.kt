package com.nextgen.newsapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.nextgen.newsapp.data.remote.api.UserApiService
import com.nextgen.newsapp.data.remote.dto.UserResponse
import com.nextgen.newsapp.helper.Async

class UserRepository(
    private val userApiService: UserApiService
) {
    private companion object{
        const val LOGIN = "ribakmasude"
    }
    suspend fun getGithubProfile(): LiveData<Async<UserResponse>> = liveData {
        emit(Async.Loading)
        try {
            val response = userApiService.getGithubProfile(LOGIN)
            if (response.isSuccessful){
                val responseBody = response.body()
                if (responseBody!= null){
                    emit(Async.Success(responseBody))
                }
            }else{
                emit(Async.Error(response.errorBody().toString()))
            }
        }catch (e: Exception){
            emit(Async.Error(e.message.toString()))
        }
    }
}