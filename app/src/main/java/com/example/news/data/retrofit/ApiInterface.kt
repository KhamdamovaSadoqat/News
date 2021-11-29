package com.example.news.data.retrofit

import com.example.news.data.model.Article
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.GET

interface ApiInterface {

    //2f6ea8d8dca7458d8406058209fd0dac mekhrilloeva
    @GET("/v2/top-headlines?country=ru&apiKey=2f6ea8d8dca7458d8406058209fd0dac")
    fun getNewsRu(): Observable<Article>

    @GET("/v2/everything?apiKey=2f6ea8d8dca7458d8406058209fd0dac")
    fun getSearchResult(
        @Query("q") search: String
    ): Observable<Article>

}