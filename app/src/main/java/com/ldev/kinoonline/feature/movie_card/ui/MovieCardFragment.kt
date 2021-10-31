package com.ldev.kinoonline.feature.movie_card.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ldev.kinoonline.R
import com.ldev.kinoonline.databinding.FragmentMovieCardBinding
import com.ldev.kinoonline.feature.base.loadImage
import com.ldev.kinoonline.feature.main_screen.domain.model.Movie

class MovieCardFragment: Fragment(R.layout.fragment_movie_card) {
    companion object {
        const val KEY_MOVIE = "movie"
        fun newInstance(movie: Movie) = MovieCardFragment().apply {
            arguments = bundleOf(Pair(KEY_MOVIE, movie))
        }
    }

    private val binding by viewBinding(FragmentMovieCardBinding::bind)

    private val movie: Movie by lazy { requireArguments().getParcelable(KEY_MOVIE)!! }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ivImageToolbar.loadImage(movie.posterPath)
            //toolbar.title = movie.title
            ctToolbar.title = movie.title
        }
    }
}