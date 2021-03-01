package com.onuryahsi.newsapp.ui.favorite

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.onuryahsi.newsapp.databinding.ActivityFavoriteWebContentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteWebContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteWebContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteWebContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        this.title = "Article Source"

        val url = intent.getStringExtra("article.url")
        if (!url.isNullOrEmpty()) {
            binding.favoriteArticleWebView.webViewClient = WebViewClient()
            binding.favoriteArticleWebView.loadUrl(url.toString())
        }
    }
}