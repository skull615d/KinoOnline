package com.ldev.kinoonline.feature.main_screen.data.api

import com.ldev.kinoonline.feature.main_screen.data.api.model.MainModel

interface MainScreenRepo {
    suspend fun getMovies(): MainModel
}