package com.example.news.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.news.R
import com.google.android.material.snackbar.BaseTransientBottomBar

class InternetSnackbar private constructor(
    parent: ViewGroup, content: View,
    callback: com.google.android.material.snackbar.ContentViewCallback
) : BaseTransientBottomBar<InternetSnackbar>(parent, content, callback) {

    fun setText(text: CharSequence): InternetSnackbar {
        val textView = getView().findViewById<View>(R.id.snackbar_text) as TextView
        textView.text = text
        return this
    }

    private class CustomContentViewCallback(private val content: View) :
        com.google.android.material.snackbar.ContentViewCallback {

        override fun animateContentIn(delay: Int, duration: Int) {
        }

        override fun animateContentOut(delay: Int, duration: Int) {
        }
    }

    companion object {
        fun make(parent: ViewGroup, duration: Int): InternetSnackbar {
            val inflater = LayoutInflater.from(parent.context)
            val content = inflater.inflate(R.layout.snackbar_internet, parent, false)
            val viewCallback = CustomContentViewCallback(content)

            return InternetSnackbar(parent, content, viewCallback).run {
                getView().setPadding(0, 0, 0, 0)
                this.duration = duration
                this
            }
        }
    }
}