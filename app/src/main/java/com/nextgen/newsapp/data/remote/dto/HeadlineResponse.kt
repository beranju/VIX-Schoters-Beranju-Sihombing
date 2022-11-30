package com.nextgen.newsapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HeadlineResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("articles")
	val articles: List<ArticlesItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)