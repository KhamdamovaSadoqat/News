package com.example.news.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "FavouriteArticles")
data class FavouriteArticles(

    @field:SerializedName("publishedAt")
    val publishedAt: String? = null,

    @field:SerializedName("author")
    var author: String? = null,

    @field:SerializedName("urlToImage")
    val urlToImage: String? = "",

    @field:SerializedName("description")
    val description: String? = "",

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("content")
    val content: String? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0

    )