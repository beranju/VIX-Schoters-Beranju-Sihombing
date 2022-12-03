package com.nextgen.newsapp.data.local.database

import android.icu.text.CaseMap.Title
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.nextgen.newsapp.data.remote.dto.Source
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Entity(tableName = "news")
@Parcelize
data class News (

    @field:PrimaryKey
    @field:ColumnInfo(name = "title")
    var title: String,

    @field:ColumnInfo(name = "publishedAt")
    val publishedAt: String? = null,

    @field:ColumnInfo(name = "author")
    val author: String? = null,

    @field:ColumnInfo(name = "urlToImg")
    val urlToImage: String? = null,

    @field:ColumnInfo(name = "description")
    val description: String? = null,

    @field:ColumnInfo(name = "sourceName")
    val sourceName: String? = null,

    @field:ColumnInfo(name = "url")
    val url: String? = null,

    @field:ColumnInfo(name = "isSaved")
    var isSaved: Boolean = false
    ): Parcelable