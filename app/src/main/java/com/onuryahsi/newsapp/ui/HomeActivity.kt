package com.onuryahsi.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.onuryahsi.newsapp.R
import com.onuryahsi.newsapp.databinding.ActivityHomeBinding
import com.onuryahsi.newsapp.ui.favorite.FavoriteNewsFragment
import com.onuryahsi.newsapp.ui.news.NewsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar.root)
        supportActionBar!!.setDisplayShowCustomEnabled(true)

        this.title = "NewsApp"

        supportFragmentManager.beginTransaction().replace(R.id.frame_container, NewsFragment())
            .commit()

        binding.bottomNavView.setOnNavigationItemSelectedListener(listener)
    }

    private var selectedFragment: Fragment = Fragment()
    private val listener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.main_menu -> {
                selectedFragment = NewsFragment()
            }
            R.id.favorite_menu -> {
                selectedFragment = FavoriteNewsFragment()
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, selectedFragment)
            .commit()

        return@OnNavigationItemSelectedListener true
    }.also {
        BottomNavigationView.OnNavigationItemReselectedListener {
            return@OnNavigationItemReselectedListener
        }
    }

}