package com.ldev.kinoonline.feature.movie_card.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ldev.kinoonline.R
import com.ldev.kinoonline.databinding.FragmentMovieCardBinding
import com.ldev.kinoonline.feature.base.loadImage
import com.ldev.kinoonline.feature.base.toStringFormat
import com.ldev.kinoonline.feature.main_screen.domain.model.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieCardFragment : Fragment(R.layout.fragment_movie_card) {
    companion object {
        const val KEY_MOVIE = "movie"
        const val KEY_SIMILAR_MOVIES = "similarMovies"
        fun newInstance(movie: Movie, similarMovies: List<Movie>) = MovieCardFragment().apply {
            arguments = bundleOf(Pair(KEY_MOVIE, movie), Pair(KEY_SIMILAR_MOVIES, similarMovies))
        }
    }

    private val viewModel by viewModel<MovieCardViewModel>()
    private val binding by viewBinding(FragmentMovieCardBinding::bind)
    private val movie: Movie by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments().getParcelable(
            KEY_MOVIE
        )!!
    }
    private val similarMovies: List<Movie> by lazy {
        requireArguments().getParcelable(
            KEY_SIMILAR_MOVIES
        )!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            movie.apply {
                bPlay.setOnClickListener { viewModel.processUiEvent(UiEvent.OnPlayClick(video)) }
                motionContainer.transitionToEnd()
                ivImageToolbar.loadImage(posterPath)
                tvTitle.text = title
                ivPoster.loadImage(posterPath)
                tvDescription.text = listOf(
                    releaseDate.toStringFormat("yyyy"),
                    genres.joinToString()
                ).joinToString(" | ")
                tvOverview.text = overview
                tvVoteAverage.text =
                    getString(R.string.vote_average_count, voteAverage, voteCount)
            }
        }
    }
}