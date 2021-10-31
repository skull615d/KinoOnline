package com.ldev.kinoonline.feature.main_screen.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Movie(
    val adult: Boolean,
    val genres: List<Genre>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: Calendar?,
    val posterPath: String,
    val popularity: Double,
    val title: String,
    val video: String,
    val voteAverage: Double,
    val voteCount: Double
) : Parcelable