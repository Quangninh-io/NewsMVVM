package com.iodev.myapplication.ui

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iodev.myapplication.model.Article
import com.iodev.myapplication.model.NewsResponse
import com.iodev.myapplication.repository.NewsRepository
import com.iodev.myapplication.util.Resource
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response

class NewsViewModel(
    val application: Application,
    val newsRepository: NewsRepository,
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        safeBreakingNewsCall(countryCode)
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
       safeSearchNewsCall(searchQuery)
    }

    private suspend fun safeBreakingNewsCall(countryCode: String){
        breakingNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()){
                val response = newsRepository.getBreakingNews(countryCode, "business")
                breakingNews.postValue(handleBreakingNewsResponse(response))
            }else{
                breakingNews.postValue(Resource.Error("No internet connecttion"))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> breakingNews.postValue(Resource.Error("Network Failurrre"))
                else -> breakingNews.postValue(Resource.Error("Conversion error"))
            }
        }
    }

    private suspend fun safeSearchNewsCall(searchQuery: String){
        searchNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()){
                val response = newsRepository.searchNews(searchQuery, searchNewsPage)
                searchNews.postValue(handleSearchNewsResponse(response))
            }else{
                searchNews.postValue(Resource.Error("No internet connecttion"))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> searchNews.postValue(Resource.Error("Network Failurrre"))
                else -> searchNews.postValue(Resource.Error("Conversion error"))
            }
        }
    }


    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticle = resultResponse.articles
                    oldArticles?.addAll(newArticle)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSaveNews() = newsRepository.getSaveNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            application.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (networkCapabilities != null) {
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
        return false
    }
}


