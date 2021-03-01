package com.onuryahsi.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.onuryahsi.newsapp.R
import com.onuryahsi.newsapp.model.Article

class NewsPagingAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Article, NewsPagingAdapter.NewsPagingViewHolder>(ARTICLE_COMPARATOR) {

    inner class NewsPagingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsTitle: TextView = itemView.findViewById(R.id.news_title)
        val newsDesc: TextView = itemView.findViewById(R.id.news_desc)
        val newsImage: ImageView = itemView.findViewById(R.id.news_image)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(getItem(bindingAdapterPosition)!!)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsPagingAdapter.NewsPagingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_news, parent, false)
        return NewsPagingViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsPagingAdapter.NewsPagingViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.newsTitle.text = currentItem?.title.toString()
        holder.newsDesc.text = currentItem?.description.toString()
        Glide.with(holder.newsImage).load(currentItem?.urlToImage.toString())
            .into(holder.newsImage)
    }

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.publishedAt == newItem.publishedAt

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(article: Article)
    }
}