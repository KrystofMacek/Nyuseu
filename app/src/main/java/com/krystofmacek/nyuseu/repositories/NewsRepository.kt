package com.krystofmacek.nyuseu.repositories

import com.krystofmacek.nyuseu.api.RetrofitInstance
import com.krystofmacek.nyuseu.database.ArticleDatabase

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        // calling the getBreaking news of NewsAPI Interface
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
}