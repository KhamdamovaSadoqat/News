package com.example.news.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.data.database.FavouriteArticlesDao
import com.example.news.data.model.ArticlesItem
import com.example.news.data.model.FavouriteArticles
import com.example.news.data.repository.FavouriteRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

open class SwipeToAdd(context: Context, dataSource: FavouriteArticlesDao) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val deleteIcon =
        ContextCompat.getDrawable(context, R.drawable.ic_post_add)
    private val intrinsicWidth = deleteIcon?.intrinsicWidth
    private val intrinsicHeight = deleteIcon?.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#FC8322")
    private var article: FavouriteArticles? = null
    private val repo = FavouriteRepository(dataSource)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    @DelicateCoroutinesApi
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        GlobalScope.launch{
            withContext(IO){
                Log.d("----------", "onSwiped: isExisting: ${repo.isTitleExisting(article!!.title)}")
                if(repo.isTitleExisting(article!!.title) == 0)
                    repo.insertFavouriteArticle(article!!)
            }

        }
    }

    fun getArticle(article: ArticlesItem) {
        this.article = FavouriteArticles(
            article.publishedAt,
            article.author,
            article.urlToImage,
            article.description,
            article.title,
            article.url,
            article.content
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top


        background.color = backgroundColor
        background.setBounds(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        background.draw(c)

        val iconTop = itemView.top + (itemHeight - intrinsicHeight!!) / 2
        val iconMargin = (itemHeight - intrinsicHeight) / 2
        val iconLeft = itemView.right - iconMargin - intrinsicWidth!!
        val iconRight = itemView.right - iconMargin
        val iconBottom = iconTop + intrinsicHeight

        deleteIcon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        deleteIcon?.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}