package com.onuryahsi.newsapp.ui.news

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.onuryahsi.newsapp.adapter.NetworkLoadStateAdapter
import com.onuryahsi.newsapp.adapter.NewsPagingAdapter
import com.onuryahsi.newsapp.databinding.FragmentNewsBinding
import com.onuryahsi.newsapp.model.Article
import com.onuryahsi.newsapp.viewModel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment(), NewsPagingAdapter.OnItemClickListener {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    //private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)

        requireActivity().title = "News"

        binding.recyclerviewNews.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }

        // viewModelFactory = context?.let { ViewModelFactory(context = it) }!!
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        binding.searchNews.isIconifiedByDefault = false
        binding.searchNews.clearFocus()
        binding.recyclerviewNews.requestFocus()

        binding.searchNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && !query.isNullOrEmpty()) {
                    viewModel.searchArticles(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && !newText.isNullOrEmpty()) {
                    //viewModel.searchArticles(newText)
                }
                return false
            }

        })

        val adapter = NewsPagingAdapter(this)
        viewModel.articles.observe(viewLifecycleOwner, Observer {
            binding.recyclerviewNews.adapter = adapter.withLoadStateFooter(
                footer = NetworkLoadStateAdapter { adapter.retry() }
            )
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        hideKeyboard()
        binding.searchNews.clearFocus()
        binding.recyclerviewNews.requestFocus()
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (imm.isAcceptingText) {
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }

    override fun onItemClick(article: Article) {
        val intent = Intent(activity, NewsDetailActivity::class.java)
        intent.putExtra("article.title", "" + article.title.toString())
        intent.putExtra("article.author", "" + article.author.toString())
        intent.putExtra("article.publishedAt", "" + article.publishedAt.toString())
        intent.putExtra("article.description", "" + article.description.toString())
        intent.putExtra("article.content", "" + article.content.toString())
        intent.putExtra("article.urlToImage", "" + article.urlToImage.toString())
        intent.putExtra("article.url", "" + article.url.toString())
        startActivity(intent)
    }
}