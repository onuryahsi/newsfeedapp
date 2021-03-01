package com.onuryahsi.newsapp.ui.news

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.onuryahsi.newsapp.databinding.ActivityNewsWebContentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsWebContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsWebContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsWebContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        this.title = "Article Source"

        val url = intent.getStringExtra("article.url")
        if (!url.isNullOrEmpty()) {
            binding.articleWebView.webViewClient = WebViewClient()
            binding.articleWebView.loadUrl(url.toString())
        }

    }
}