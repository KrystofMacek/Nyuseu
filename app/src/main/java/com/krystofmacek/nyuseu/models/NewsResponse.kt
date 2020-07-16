package com.krystofmacek.nyuseu.models

import com.krystofmacek.nyuseu.models.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)