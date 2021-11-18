package com.ldev.kinoonline.feature.main_screen.data

import com.ldev.kinoonline.feature.base.toCalendar
import com.ldev.kinoonline.feature.main_screen.data.api.model.MainModel
import com.ldev.kinoonline.feature.main_screen.data.api.model.MoveModel
import com.ldev.kinoonline.feature.main_screen.domain.model.Main
import com.ldev.kinoonline.feature.main_screen.domain.model.Movie

fun MoveModel.toDomain() = Movie(
    adult = adult,
    genres = genres.map { it.name },
    id = id,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    releaseDate = releaseDate.toCalendar(),
    posterPath = posterPath,
    popularity = popularity,
    title = title,
    video = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",//video,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun MainModel.toDomain() = Main(
    page = page,
    results = results.map { it.toDomain() },
    totalPages = totalPages,
    totalResults = totalResults
)