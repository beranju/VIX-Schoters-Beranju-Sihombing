package com.nextgen.newsapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nextgen.newsapp.data.repository.UserRepository

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    suspend fun getGithubProfile() = userRepository.getGithubProfile()
}