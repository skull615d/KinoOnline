package com.ldev.kinoonline.feature.main_screen.ui

import com.ldev.kinoonline.feature.base.view_model.Sorting
import com.ldev.kinoonline.feature.main_screen.domain.model.Movie

fun List<Movie>.sortedMovies(sorting: Sorting?): List<Movie> {
    return when (sorting) {
        is SortedBy.Date -> {
            this.sortedByDescending { it.releaseDate }
        }
        is SortedBy.Name -> {
            this.sortedBy { it.title }
        }
        is SortedBy.Popularity -> {
            this.sortedByDescending { it.popularity }
        }
        is SortedBy.Rating -> {
            this.sortedByDescending { it.voteAverage }
        }
        else -> this
    }
}