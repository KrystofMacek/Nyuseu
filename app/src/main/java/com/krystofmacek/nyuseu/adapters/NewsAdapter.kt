package com.krystofmacek.nyuseu.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.krystofmacek.nyuseu.R
import com.krystofmacek.nyuseu.models.Article
import kotlinx.android.synthetic.main.item_article_preview.view.*

/**
 * Adapter for creating items in news Recycler View
 * Created with Diff Util to manage lists
 * */
class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    // differCallback will be anonymous class, implementing inherited functions
    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            // not using ID bcs if the articles from API doesn't have one
            // checking if the articles are same, using their unique url
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    // List differ - manages the lists
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_article_preview,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    // Binding the views with data from article
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            // Load image with Glide
            Glide.with(this).load(article.urlToImage).into(ivArticleImage)
            // set the textviews
            tvSource.text = article.source?.name
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt


            setOnClickListener {
                onItemClickListener?.let {
                    it(article)
                }
            }

        }
    }

    // Setup click listener
    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

}