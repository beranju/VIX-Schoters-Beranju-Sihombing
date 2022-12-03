package com.nextgen.newsapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [News::class, RemoteKeys::class], version = 4, exportSchema = false)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object{
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): NewsDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java, "quote_database"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}