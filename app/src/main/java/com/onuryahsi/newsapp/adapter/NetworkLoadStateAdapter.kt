package com.onuryahsi.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onuryahsi.newsapp.R

class NetworkLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<NetworkLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val footerRetry: Button = itemView.findViewById(R.id.footer_retry)
        val footerErrDesc: TextView = itemView.findViewById(R.id.footer_error_desc)
        val footerProgressBar: ProgressBar = itemView.findViewById(R.id.footer_progress_bar)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.footerRetry.isVisible = loadState !is LoadState.Loading
        holder.footerErrDesc.isVisible = loadState !is LoadState.Loading
        holder.footerProgressBar.isVisible = loadState is LoadState.Loading

        if (loadState is LoadState.Error) {
            holder.footerErrDesc.text = loadState.error.localizedMessage
        }
        holder.footerRetry.setOnClickListener {
            retry.invoke()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.load_state_footer, parent, false)
        return LoadStateViewHolder(v)
    }
}