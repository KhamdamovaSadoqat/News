package com.example.news.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.news.data.model.FavouriteArticles

@Database(entities = [FavouriteArticles::class], version = 1, exportSchema = true)
abstract class RoomDatabase : androidx.room.RoomDatabase() {
    abstract val favouriteArticlesDao: FavouriteArticlesDao

    companion object {
        private var INSTANCE: RoomDatabase? = null

        fun getDatabase(context: Context): RoomDatabase {
            if (INSTANCE != null) {
                return INSTANCE!!
            }
            synchronized(this) {
                val database = Room.databaseBuilder(context, RoomDatabase::class.java, "News")
                    .fallbackToDestructiveMigration().build()

                INSTANCE = database
                return INSTANCE!!
            }
        }
    }
}