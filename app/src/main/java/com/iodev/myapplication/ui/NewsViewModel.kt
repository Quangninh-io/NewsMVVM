package com.iodev.myapplication.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iodev.myapplication.model.NewsReponse
import com.iodev.myapplication.repository.NewsRepository
import com.iodev.myapplication.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {
    val breakingNews: MutableLiveData<Resource<NewsRepository>> = MutableLiveData()
    var breakingNewsPage = 1

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNew(countryCode, breakingNewsPage)
        breakingNews.postValue(Resource.Success(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsReponse>): Resource<NewsReponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}

private fun <T> MutableLiveData<T>.postValue(success: T) {

}
