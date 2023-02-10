package com.iodev.myapplication.ui.fragment

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.iodev.myapplication.R
import com.iodev.myapplication.db.ArticleDatabase
import com.iodev.myapplication.model.Article
import com.iodev.myapplication.model.Source
import com.iodev.myapplication.repository.NewsRepository
import com.iodev.myapplication.ui.NewsViewModel
import com.iodev.myapplication.ui.NewsViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel
    private val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsRepository = activity?.let { NewsRepository(ArticleDatabase(it)) }
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository!!)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
        val article: Article = Article(
            null,
            "",
            args.content,
            args.description,
            args.publishedAt,
            Source("", ""),
            args.title,
            args.url,
            args.urlToImage,
        )

        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view,"Article saved successfully!",Snackbar.LENGTH_SHORT).show()
        }
    }
}