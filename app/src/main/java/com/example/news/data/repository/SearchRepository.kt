package com.example.news.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.news.data.model.Article
import com.example.news.data.model.remote.Resource
import com.example.news.data.model.remote.Status
import com.example.news.data.retrofit.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchRepository {
    private val compositeDisposable = CompositeDisposable()
    private val apiClient = ApiClient.getApiClient()

    fun searchArticle(search: String, response: MutableLiveData<Resource<Article>>){
        compositeDisposable.add(
            apiClient.getSearchResult(search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableObserver<Article>(){
                    override fun onNext(t: Article) {
                        response.value = Resource(Status.SUCCESS, t, null, null)
                    }

                    override fun onError(e: Throwable) {
                        response.value = Resource(Status.ERROR, null, e.message, e)
                    }

                    override fun onComplete() {}
                })
        )
        response.value = Resource(Status.LOADING, null, null, null)
    }
}