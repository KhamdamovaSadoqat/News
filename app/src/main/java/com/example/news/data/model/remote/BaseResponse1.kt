package com.example.news.data.model.remote

data class BaseResponse1<T>(
    val data:T,
    val message:String,
    val statusCode:Int
)
