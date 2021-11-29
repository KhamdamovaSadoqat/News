package com.example.news.data.model.remote

const val SC_DEFAULT: Int = -500

data class BaseResponse<T> (
    val statusCode:Int,
    val data:T,
    val message:String
)