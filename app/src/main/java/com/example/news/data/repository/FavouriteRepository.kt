package com.example.news.data.repository

import androidx.lifecycle.LiveData
import com.example.news.data.database.FavouriteArticlesDao
import com.example.news.data.model.FavouriteArticles

class FavouriteRepository(private val dataSource: FavouriteArticlesDao) {

    suspend fun insertFavouriteArticle(article: FavouriteArticles) {
        dataSource.insertFavouriteArticle(article)
    }

    fun isTitleExisting(title: String?): Int {
        return dataSource.isDataExist(title)
    }

    fun getFavouriteArticles(): LiveData<List<FavouriteArticles>> {
        return dataSource.getFavouriteArticles()
    }

    fun deleteArticle(id: Int){
        dataSource.deleteArticle(id)
    }

    fun removeAll() {
        dataSource.removeAll()
    }


}