package com.krystofmacek.nyuseu.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.krystofmacek.nyuseu.models.Article

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    // Implementation is created by Room
    abstract fun getArticleDao(): ArticleDao

    companion object {
        // When thread changes this instance.. other threads can immediately see
        @Volatile
        // Singleton instance of database
        private var instance: ArticleDatabase? = null

        // Loc is used to synchronize instance
        private val LOCK = Any()

        // Called when we create database (when the object is instantiated), we return instance of DB
        // If instance is null we start synchronize block, -> we deny any concurrent access of threads
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){

            // if the instance is still null we create the database
            instance ?: createDatabase(context).also {
                // and set the instance to result of createDatabase function
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ArticleDatabase::class.java,
            "article_db.db"
        ).build()

    }
}