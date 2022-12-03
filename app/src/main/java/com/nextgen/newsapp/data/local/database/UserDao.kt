package com.nextgen.newsapp.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nextgen.newsapp.data.remote.dto.UserResponse

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUser(): LiveData<UserResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserResponse)
}