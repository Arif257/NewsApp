package com.example.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.ui.db.ArticleDatabase
import com.example.newsapp.ui.fragment.*
import com.example.newsapp.ui.repository.NewsRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_news.*


class NewsActivity : AppCompatActivity(), FC {


    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        loadFragment(BreakingNewsFragment())

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]

    //    bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.breakingNewsFragment -> {
                    loadFragment(BreakingNewsFragment())
                }
                R.id.searchNewsFragment -> {
                    loadFragment(SearchNewsFragment())
                }
                R.id.savedNewsFragment -> {
                    loadFragment(SavedNewsFragment())
                }
            }
            true
        }



    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.newsNavHostFragment,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun passData(text: String) {
        if(text == "newsDetails"){
            loadFragment(ArticleFragment())
        }
    }

}
