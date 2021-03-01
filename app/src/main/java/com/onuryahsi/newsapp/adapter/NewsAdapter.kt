package com.onuryahsi.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.onuryahsi.newsapp.R
import com.onuryahsi.newsapp.model.Article

class NewsAdapter(private val list: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsTitle: TextView = itemView.findViewById(R.id.news_title)
        val newsDesc: TextView = itemView.findViewById(R.id.news_desc)
        val newsImage: ImageView = itemView.findViewById(R.id.news_image)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsAdapter.NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        holder.newsTitle.text = list[position].title.toString()
        holder.newsDesc.text = list[position].description.toString()
        Glide.with(holder.newsImage).load(list[position].urlToImage.toString())
            .into(holder.newsImage)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}