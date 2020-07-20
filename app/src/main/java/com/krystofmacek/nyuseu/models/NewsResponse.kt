package com.krystofmacek.nyuseu.models

import com.krystofmacek.nyuseu.models.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)