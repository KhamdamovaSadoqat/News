package com.example.news.ui.main.favourites

import androidx.lifecycle.LiveData
import com.example.news.data.database.FavouriteArticlesDao
import com.example.news.data.model.FavouriteArticles
import com.example.news.data.repository.FavouriteRepository
import com.example.news.ui.base.BaseVM

class FavouritesViewModel(dataSource: FavouriteArticlesDao) : BaseVM() {

    private val favouriteArticle: LiveData<List<FavouriteArticles>>
    private val repo = FavouriteRepository(dataSource)

    init{
        favouriteArticle = repo.getFavouriteArticles()
    }

    fun getFavouriteArticle(): LiveData<List<FavouriteArticles>>{
        return favouriteArticle
    }

    fun removeAll(){
        repo.removeAll()
    }


}