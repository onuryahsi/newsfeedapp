package com.onuryahsi.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.onuryahsi.newsapp.R
import com.onuryahsi.newsapp.schema.Article

class FavoritesAdapter(private val list: List<Article>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesHolder>() {

    inner class FavoritesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsTitle: TextView = itemView.findViewById(R.id.news_title)
        val newsDesc: TextView = itemView.findViewById(R.id.news_desc)
        val newsImage: ImageView = itemView.findViewById(R.id.news_image)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(list[bindingAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesAdapter.FavoritesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_news, parent, false)
        return FavoritesHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritesAdapter.FavoritesHolder, position: Int) {
        holder.newsTitle.text = list[position].title.toString()
        holder.newsDesc.text = list[position].description.toString()
        Glide.with(holder.newsImage).load(list[position].urlToImage.toString())
            .into(holder.newsImage)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(article: Article)
    }
}