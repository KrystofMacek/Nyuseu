package com.krystofmacek.nyuseu.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.krystofmacek.nyuseu.R
import com.krystofmacek.nyuseu.ui.NewsActivity
import com.krystofmacek.nyuseu.ui.NewsViewModel

/**
 * A simple [Fragment] subclass.
 */
class SavedNewsFragment : Fragment() {

    lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
    }

}
