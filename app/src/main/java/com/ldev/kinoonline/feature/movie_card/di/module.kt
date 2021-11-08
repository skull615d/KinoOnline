package com.ldev.kinoonline.feature.movie_card.di

import com.github.terrakok.cicerone.Router
import com.ldev.kinoonline.feature.movie_card.ui.MovieCardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moduleMovieCard = module {

    viewModel {
        MovieCardViewModel(get<Router>())
    }
}