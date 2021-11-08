package com.ldev.kinoonline.feature.base

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.ldev.kinoonline.feature.main_screen.domain.model.Movie
import com.ldev.kinoonline.feature.main_screen.ui.MoviesListFragment
import com.ldev.kinoonline.feature.movie_card.ui.MovieCardFragment
import com.ldev.kinoonline.feature.player.ui.PlayerFragment

object Screens {
    fun MainScreen() = FragmentScreen { MoviesListFragment.newInstance() }

    fun MovieCard(movie: Movie, similarMovies: List<Movie>) =
        FragmentScreen {
            MovieCardFragment.newInstance(
                movie = movie,
                similarMovies = similarMovies
            )
        }

    fun Player(url: String) = FragmentScreen { PlayerFragment.newInstance(movieUrl = url) }
}