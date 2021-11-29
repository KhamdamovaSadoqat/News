package com.example.news.ui.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.news.data.database.FavouriteArticlesDao
import com.example.news.data.model.Article
import com.example.news.data.model.remote.Resource
import com.example.news.data.repository.ArticleRepository
import com.example.news.ui.base.BaseVM

class HomeViewModel : BaseVM() {

    private val articleRu = MutableLiveData<Resource<Article>>()
    private val repo = ArticleRepository()

    fun getArticleRu(): LiveData<Resource<Article>>{
        Log.d("----------", "getArticleRu: ww")
        repo.getArticleRu(articleRu)
        return articleRu
    }

}