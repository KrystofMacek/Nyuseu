package com.krystofmacek.nyuseu.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krystofmacek.nyuseu.models.NewsResponse
import com.krystofmacek.nyuseu.repositories.NewsRepository
import com.krystofmacek.nyuseu.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
): ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val breakingNewsPage = 1

    init {
        getBreakingNews("us")
    }

    // here we need to start coroutine to call the suspend functions of getting news
    // viewModelScope - coroutine is alive as long as view model is alive
    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        // set breaking news state to Loading
        breakingNews.postValue(Resource.Loading())
        // get the response
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        // set breaking news based on response
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {

        if(response.isSuccessful) {
            // if body not null
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}