package com.example.news.ui.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.data.model.ArticlesItem
import com.example.news.databinding.ItemArticleBinding
import com.example.news.utils.ImageDownloader
import com.example.news.utils.DateTimeUtils

class HomeAdapter(private val itemClickListener: (ArticlesItem) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.VH>() {

    private var listItem = listOf<ArticlesItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listItem: List<ArticlesItem>) {
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
        holder.itemView.setOnClickListener {
            itemClickListener.invoke(listItem[position])
        }
        holder.onBind(listItem[position])
    }

    override fun getItemCount() = listItem.size

    class VH(private val binding: ItemArticleBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(article: ArticlesItem) {
            binding.apply {
                tvArticleHeader.text = article.title
                tvArticleAuthor.text = article.author
                tvArticlePublishDate.text = DateTimeUtils.stringToDateString(article.publishedAt)
                article.urlToImage.let { ImageDownloader.loadImage(context, it, sivArticlePhoto) }
            }
        }
    }

}