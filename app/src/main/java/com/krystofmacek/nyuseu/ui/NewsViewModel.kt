package com.krystofmacek.nyuseu.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krystofmacek.nyuseu.models.Article
import com.krystofmacek.nyuseu.models.NewsResponse
import com.krystofmacek.nyuseu.repositories.NewsRepository
import com.krystofmacek.nyuseu.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
): ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null

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
                // every time we get response increase page by 1
                breakingNewsPage++
                // if its the first page
                if(breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    // add new page of articles to list of already loaded
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            // if body not null
            response.body()?.let { resultResponse ->
                // every time we get response increase page by 1
                searchNewsPage++
                // if its the first page
                if(searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    // add new page of articles to list of already loaded
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    // DB functions
    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.insert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

}