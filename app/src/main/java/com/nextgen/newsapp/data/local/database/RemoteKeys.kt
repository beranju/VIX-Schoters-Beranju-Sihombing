package com.nextgen.newsapp.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    var title: String,
    val prevKey: Int?,
    val nextKey: Int?

)