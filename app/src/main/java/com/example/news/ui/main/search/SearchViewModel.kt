package com.example.news.ui.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.news.data.model.Article
import com.example.news.data.model.remote.Resource
import com.example.news.data.repository.SearchRepository
import com.example.news.ui.base.BaseVM

class SearchViewModel : BaseVM() {

    private val articleRu = MutableLiveData<Resource<Article>>()
    private val repo = SearchRepository()

    fun getSearchArticle(search: String): LiveData<Resource<Article>>{
        repo.searchArticle(search, articleRu)
        return articleRu
    }


}