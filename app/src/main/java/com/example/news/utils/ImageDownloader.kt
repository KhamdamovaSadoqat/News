package com.example.news.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.news.R
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

object ImageDownloader {
    @SuppressLint("CheckResult")
    fun loadImage(
        context: Context,
        url: String,
        imageView: ImageView,
        @DrawableRes
        placeHolderRes: Int? = null,
        @DrawableRes
        errorRes: Int? = null
    ) {
        val uri = Uri.parse(url)
        val separated = url.split(".")
        if (separated.last() == "svg") {

            GlideToVectorYou
                .init()
                .apply {
                    with(context)
                    if (placeHolderRes != null && errorRes != null)
                        setPlaceHolder(placeHolderRes, errorRes)
                    else
                        setPlaceHolder(R.drawable.ic_image_placeholder, R.drawable.ic_loading_error)
                    load(uri, imageView)
                }


        } else {
            Glide.with(context)
                .load(uri)
                .apply {
                    if (placeHolderRes != null)
                        placeholder(placeHolderRes)
                    else
                        placeholder(R.drawable.ic_image_placeholder)
                    if (errorRes != null)
                        error(errorRes)
                    else
                        error(R.drawable.ic_loading_error)
                    into(imageView)
                }


        }
    }
}