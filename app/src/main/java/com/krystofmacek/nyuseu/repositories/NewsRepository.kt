package com.krystofmacek.nyuseu.repositories

import com.krystofmacek.nyuseu.api.RetrofitInstance
import com.krystofmacek.nyuseu.database.ArticleDatabase
import com.krystofmacek.nyuseu.models.Article

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        // calling the getBreaking news of NewsAPI Interface
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)


    //db
    suspend fun insert(article: Article) = db.getArticleDao().insert(article)
    fun getSavedNews() = db.getArticleDao().getAllArticles()
    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)


}