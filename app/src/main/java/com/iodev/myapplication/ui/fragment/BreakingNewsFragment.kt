package com.iodev.myapplication.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iodev.myapplication.R
import com.iodev.myapplication.adapter.NewsAdapter
import com.iodev.myapplication.db.ArticleDatabase
import com.iodev.myapplication.repository.NewsRepository
import com.iodev.myapplication.ui.NewsViewModel
import com.iodev.myapplication.ui.NewsViewModelProviderFactory
import com.iodev.myapplication.util.Constants.Companion.QUERY_PAGE_SIZE
import com.iodev.myapplication.util.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_breaking_news.paginationProgressBar
import kotlinx.android.synthetic.main.fragment_search_news.*

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    lateinit var newsAdapter: NewsAdapter
    lateinit var viewModel: NewsViewModel
    val TAG = "BreakingNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsRepository = activity?.let { NewsRepository(ArticleDatabase(it)) }
        val viewModelProviderFactory = NewsViewModelProviderFactory(requireActivity().application,newsRepository!!)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }

            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }

        if (::viewModel.isInitialized) {
            viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles)
                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let { message ->
                            Log.e(TAG, "An error occured: $message")
                        }
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }
            })
        } else {
            Toast.makeText(activity, "view model not installed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }


}