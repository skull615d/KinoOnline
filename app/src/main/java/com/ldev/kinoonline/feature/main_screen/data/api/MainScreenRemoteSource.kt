package com.ldev.kinoonline.feature.main_screen.data.api

import com.ldev.kinoonline.feature.main_screen.data.api.model.MainModel

class MainScreenRemoteSource(private val api: MainScreenApi) {
    suspend fun getMoves(): MainModel {
        return api.getMoves()
    }
}