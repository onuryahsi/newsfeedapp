package com.onuryahsi.newsapp.ui.news

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.onuryahsi.newsapp.R
import com.onuryahsi.newsapp.databinding.ActivityNewsDetailBinding
import com.onuryahsi.newsapp.schema.Article
import com.onuryahsi.newsapp.viewModel.NewsViewModel
import com.onuryahsi.newsapp.viewModel.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding
    private lateinit var viewModel: NewsViewModel
    //private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        this.title = "Article Details"

        //viewModelFactory = applicationContext?.let { ViewModelFactory(context = it) }!!
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        fillTheView()
        getArticleIfExists()
    }

    @SuppressLint("SetTextI18n")
    private fun fillTheView() {
        val item = intent
        val title = item.getStringExtra("article.title")
        val author = if (item.getStringExtra("article.author")
                .equals("null")
        ) "Author" else item.getStringExtra("article.author")
        val publishedAt = item.getStringExtra("article.publishedAt")
        val content = item.getStringExtra("article.content")
        val urlToImage = item.getStringExtra("article.urlToImage")
        val url = item.getStringExtra("article.url")

        var date = String()
        if (!publishedAt.isNullOrEmpty() && publishedAt.length >= 20) {
            date = parseDate(publishedAt)
        }

        binding.newsTitle.text = title
        binding.newsAuthor.text = author
        binding.newsPublishedAt.text = date
        binding.newsContent.text = content
        Glide.with(binding.root)
            .load(urlToImage)
            .into(binding.newsDetailImage)
        binding.newsSource.text = "News Source"

        binding.newsSource.setOnClickListener {
            val i = Intent(application, NewsWebContentActivity::class.java)
            i.putExtra("article.url", url)
            startActivity(i)
        }
    }

    private fun parseDate(date: String): String {

        // 2021-03-02T22:58:59Z
        val day = date.substring(8, 10)
        val month = date.substring(5, 7)
        val year = date.substring(0, 4)
        val hour = date.substring(11, 13)
        val min = date.substring(14, 16)
        val sec = date.substring(17, 19)

        return day
            .plus(".")
            .plus(month)
            .plus(".")
            .plus(year)
            .plus(" ")
            .plus(hour)
            .plus(":")
            .plus(min)
            .plus(":")
            .plus(sec)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.options_menu, menu)
        getArticleIfExists()
        if (isExists) {
            updateOptionsMenu(menu)
        }
        return true
    }

    private fun updateOptionsMenu(menu: Menu?) {
        menu?.getItem(1)?.setIcon(R.drawable.ic_baseline_favorite_24)
        menu?.getItem(1)?.isChecked = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                share()
            }
            R.id.action_save -> {
                if (item.isChecked) {
                    item.setIcon(R.drawable.ic_baseline_favorite_border_24)
                    item.isChecked = false
                    removeArticle()
                } else {
                    item.setIcon(R.drawable.ic_baseline_favorite_24)
                    item.isChecked = true
                    saveArticle()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun share() {
        val sendIntent: Intent = Intent().apply {
            val item = intent
            val url = item.getStringExtra("article.url")
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "" + url)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun saveArticle() {
        val item = intent
        val title = item.getStringExtra("article.title")
        val author = if (item.getStringExtra("article.author")
                .equals("null")
        ) "Author" else item.getStringExtra("article.author")
        val publishedAt = item.getStringExtra("article.publishedAt")
        val description = item.getStringExtra("article.description")
        val content = item.getStringExtra("article.content")
        val urlToImage = item.getStringExtra("article.urlToImage")
        val url = item.getStringExtra("article.url")

        val mArticle = Article(
            id = 0,
            title = title,
            author = author,
            publishedAt = publishedAt,
            description = description,
            urlToImage = urlToImage,
            url = url,
            content = content
        )

        getArticleIfExists()
        if (!isExists) {
            viewModel.saveArticle(mArticle)
        }
    }

    private fun removeArticle() {
        getArticleIfExists()
        if (isExists) {
            viewModel.removeArticle(article)
        }
    }

    private var isExists = false
    lateinit var article: Article
    private fun getArticleIfExists() {
        val item = intent
        val url = item.getStringExtra("article.url")
        if (url != null) {
            viewModel.searchByUrlLiveData(url).observe(this, {
                if (it != null) {
                    isExists = true
                    article = it
                } else {
                    isExists = false
                }
            })
        }
    }
}