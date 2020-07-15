package com.krystofmacek.nyuseu

import com.krystofmacek.nyuseu.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)