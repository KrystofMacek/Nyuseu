package com.krystofmacek.nyuseu.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.krystofmacek.nyuseu.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Query("SELECT * FROM articles")
    // Not suspend because we want to return LiveData
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)

}