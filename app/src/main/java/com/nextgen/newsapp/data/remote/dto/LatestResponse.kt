package com.nextgen.newsapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.nextgen.newsapp.data.local.database.News

data class LatestResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("articles")
	val articles: List<News>? = null,

	@field:SerializedName("status")
	val status: String? = null
)