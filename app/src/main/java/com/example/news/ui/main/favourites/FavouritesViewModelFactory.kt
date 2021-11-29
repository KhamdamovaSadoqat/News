package com.example.news.ui.main.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.news.data.database.FavouriteArticlesDao

class FavouritesViewModelFactory(private val dataSource: FavouriteArticlesDao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouritesViewModel::class.java)) {
            return FavouritesViewModel(dataSource) as T
        }else{
            throw  IllegalAccessException("FavouriteViewModel not found")
        }
    }
}