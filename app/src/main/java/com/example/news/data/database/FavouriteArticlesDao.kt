package com.example.news.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.news.data.model.Article
import com.example.news.data.model.ArticlesItem
import com.example.news.data.model.FavouriteArticles

@Dao
interface FavouriteArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteArticle(article: FavouriteArticles)

    @Query("SELECT * FROM FavouriteArticles WHERE title = :title")
    fun isDataExist(title: String?): Int

    @Query("SELECT * FROM FavouriteArticles")
    fun getFavouriteArticles(): LiveData<List<FavouriteArticles>>

    @Query("DELETE FROM FavouriteArticles")
    fun removeAll()

    @Query("DELETE FROM FavouriteArticles where id = :id")
    fun deleteArticle(id: Int)
}