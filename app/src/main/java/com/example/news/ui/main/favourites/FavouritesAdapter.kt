package com.example.news.ui.main.favourites

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.data.model.FavouriteArticles
import com.example.news.databinding.ItemArticleBinding
import com.example.news.utils.DateTimeUtils
import com.example.news.utils.ImageDownloader

class FavouritesAdapter : RecyclerView.Adapter<FavouritesAdapter.VH>() {

    private var listItem = listOf<FavouriteArticles>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listItem: List<FavouriteArticles>) {
        this.listItem = listItem
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemArticleBinding>(
                inflater,
                R.layout.item_article,
                parent,
                false
            )
        return VH(binding, parent.context)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(listItem[position])
    }

    override fun getItemCount() = listItem.size


    class VH(private val binding: ItemArticleBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(article: FavouriteArticles) {
            binding.apply {
                tvArticleHeader.text = article.title
                tvArticleAuthor.text = article.author
                tvArticlePublishDate.text = article.publishedAt?.let {
                    DateTimeUtils.stringToDateString(
                        it
                    )
                }
                article.urlToImage?.let { ImageDownloader.loadImage(context, it, sivArticlePhoto) }
            }
        }
    }
}