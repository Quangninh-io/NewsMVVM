package com.iodev.myapplication.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.iodev.myapplication.R
import com.iodev.myapplication.db.ArticleDatabase
import com.iodev.myapplication.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_news.*


class NewsActivity : AppCompatActivity() {

    lateinit var  viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory =NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)

        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
    }

}