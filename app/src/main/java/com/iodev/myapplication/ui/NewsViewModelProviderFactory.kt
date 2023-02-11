package com.iodev.myapplication.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.iodev.myapplication.repository.NewsRepository

class NewsViewModelProviderFactory(
    val application: Application,
    val newsRepository: NewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return NewsViewModel(application, newsRepository) as T
    }
}