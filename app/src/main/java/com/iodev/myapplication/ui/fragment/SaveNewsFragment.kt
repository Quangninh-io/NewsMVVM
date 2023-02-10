package com.iodev.myapplication.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.iodev.myapplication.R
import com.iodev.myapplication.adapter.NewsAdapter
import com.iodev.myapplication.db.ArticleDatabase
import com.iodev.myapplication.repository.NewsRepository
import com.iodev.myapplication.ui.NewsActivity
import com.iodev.myapplication.ui.NewsViewModel
import com.iodev.myapplication.ui.NewsViewModelProviderFactory
import com.iodev.myapplication.util.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_breaking_news.paginationProgressBar
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job

class SaveNewsFragment : Fragment(R.layout.fragment_saved_news) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}

