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
import com.ldev.kinoonline.feature.player.ui.PlayerFragment
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
        viewModel.singleLiveEvent.observe(viewLifecycleOwner, ::onSingeEvent)
        binding.apply {
            bPlay.setOnClickListener { viewModel.processUiEvent(UiEvent.OnPlayClick(movie.video)) }
            motionContainer.transitionToEnd()
            ivImageToolbar.loadImage(movie.posterPath)
            tvTitle.text = movie.title
            ivPoster.loadImage(movie.posterPath)
            val year = movie.releaseDate.toStringFormat("yyyy")
            val genres = movie.genres.map { it.name }.joinToString(", ", "", "")
            tvDescription.text = listOf(year, genres).joinToString(" | ")
            val overview = movie.overview + movie.overview + movie.overview + movie.overview
            tvOverview.text = overview
            tvVoteAverage.text =
                getString(R.string.vote_average_count, movie.voteAverage, movie.voteCount)
        }
    }

    private fun onSingeEvent(event: SingleEvent) {
        when (event) {
            is SingleEvent.OpenPlayer -> {
                parentFragmentManager.beginTransaction()
                    .add(R.id.mainContainer, PlayerFragment.newInstance(event.movieUrl))
                    .addToBackStack("movieCard")
                    .commit()
            }
            is SingleEvent.OpenMovieCard -> {

            }
        }
    }
}