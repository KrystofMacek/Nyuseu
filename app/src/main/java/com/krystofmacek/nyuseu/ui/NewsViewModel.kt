package com.krystofmacek.nyuseu.ui

import androidx.lifecycle.ViewModel
import com.krystofmacek.nyuseu.repositories.NewsRepository

class NewsViewModel(
    val newsRepository: NewsRepository
): ViewModel() {

}