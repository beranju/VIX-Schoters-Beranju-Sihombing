package com.nextgen.newsapp.data.local.database

import android.icu.text.CaseMap.Title
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getNews(): PagingSource<Int, News>

    @Query("SELECT * FROm news WHERE isSaved = 1")
    fun getSavedNews(): LiveData<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: List<News>)

    @Update
    fun update(news: News)

    @Query("DELETE FROM news WHERE isSaved = 0")
    fun delete()

    @Query("DELETE FROM news")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM news WHERE title = :title AND isSaved = 1)")
    fun isSaved(title: String): Boolean

}