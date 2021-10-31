package com.ldev.kinoonline.feature.main_screen.data

import com.ldev.kinoonline.feature.base.toCalendar
import com.ldev.kinoonline.feature.main_screen.data.api.model.GenreModel
import com.ldev.kinoonline.feature.main_screen.data.api.model.MainModel
import com.ldev.kinoonline.feature.main_screen.data.api.model.MoveModel
import com.ldev.kinoonline.feature.main_screen.domain.model.Genre
import com.ldev.kinoonline.feature.main_screen.domain.model.Main
import com.ldev.kinoonline.feature.main_screen.domain.model.Movie

fun GenreModel.toDomain() = Genre(
    name = name
)

fun MoveModel.toDomain() = Movie(
    adult,
    genres.map { it.toDomain() },
    id,
    originalLanguage,
    originalTitle,
    overview,
    releaseDate.toCalendar(),
    posterPath,
    popularity,
    title,
    video,
    voteAverage,
    voteCount
)

fun MainModel.toDomain() = Main(
    page,
    results.map { it.toDomain() },
    total_pages,
    total_results
)