package com.ldev.kinoonline.feature.main_screen.domain

import com.ldev.kinoonline.feature.base.attempt
import com.ldev.kinoonline.feature.main_screen.data.api.MainScreenRepo
import com.ldev.kinoonline.feature.main_screen.data.toDomain

class MoviesInteractor(private val repo: MainScreenRepo) {
    suspend fun getMovies() = attempt { repo.getMovies().toDomain() }
}