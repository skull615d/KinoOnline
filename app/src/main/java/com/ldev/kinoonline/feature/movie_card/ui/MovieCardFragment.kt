package com.ldev.kinoonline.feature.movie_card.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ldev.kinoonline.R
import com.ldev.kinoonline.databinding.FragmentMovieCardBinding
import com.ldev.kinoonline.feature.main_screen.domain.model.Movie

class MovieCardFragment: Fragment(R.layout.fragment_movie_card) {
    private val binding by viewBinding(FragmentMovieCardBinding::bind)

    companion object{
        fun newInstance(movie: Movie) = MovieCardFragment().apply {
            arguments = bundleOf(Pair("movie", movie))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}