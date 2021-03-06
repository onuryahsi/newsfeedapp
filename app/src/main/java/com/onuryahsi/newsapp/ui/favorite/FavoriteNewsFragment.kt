package com.onuryahsi.newsapp.ui.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.onuryahsi.newsapp.adapter.FavoritesAdapter
import com.onuryahsi.newsapp.databinding.FragmentFavoriteBinding
import com.onuryahsi.newsapp.schema.Article
import com.onuryahsi.newsapp.viewModel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class FavoriteNewsFragment : Fragment(), FavoritesAdapter.OnItemClickListener {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoritesViewModel

    // private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var adapter: FavoritesAdapter
    var unfilteredList = mutableListOf<Article>()
    val filteredList = mutableListOf<Article>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        requireActivity().title = "Favorites"

        // viewModelFactory = context?.let { ViewModelFactory(context = it) }!!
        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        binding.searchFavoriteNews.isIconifiedByDefault = false
        binding.searchFavoriteNews.clearFocus()
        binding.recyclerviewFavoriteNews.requestFocus()

        hideKeyboard()

        binding.recyclerviewFavoriteNews.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        viewModel.getAll().observe(viewLifecycleOwner, {
            unfilteredList.clear()
            unfilteredList = it as MutableList<Article>
            adapter = FavoritesAdapter(unfilteredList, this)
            binding.recyclerviewFavoriteNews.adapter = adapter
        })

        fun updateResult(_filteredList: MutableList<Article>) {
            adapter = FavoritesAdapter(_filteredList, this)
            binding.recyclerviewFavoriteNews.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        binding.searchFavoriteNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filteredList.clear()
                for (item in unfilteredList) {
                    if (item.title?.toLowerCase(Locale.getDefault())
                            ?.contains(query.toString().toLowerCase(Locale.getDefault()))!!
                    ) {
                        filteredList.add(item)
                    }
                }
                updateResult(filteredList)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filteredList.clear()
                for (item in unfilteredList) {
                    if (item.title?.toLowerCase(Locale.getDefault())
                            ?.contains(newText.toString().toLowerCase(Locale.getDefault()))!!
                    ) {
                        filteredList.add(item)
                    }
                }
                updateResult(filteredList)
                return false
            }

        })

        return binding.root
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (imm.isAcceptingText) {
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }

    override fun onResume() {
        super.onResume()
        hideKeyboard()
        binding.searchFavoriteNews.clearFocus()
        binding.recyclerviewFavoriteNews.requestFocus()
    }

    override fun onItemClick(article: Article) {
        val intent = Intent(activity, FavoriteDetailActivity::class.java)
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