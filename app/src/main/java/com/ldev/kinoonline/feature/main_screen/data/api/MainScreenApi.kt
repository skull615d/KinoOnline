package com.ldev.kinoonline.feature.main_screen.data.api

import com.ldev.kinoonline.feature.base.constants.Constants
import com.ldev.kinoonline.feature.main_screen.data.api.model.MainModel
import retrofit2.http.GET

interface MainScreenApi {
    @GET(Constants.BASE_MOVIES_PATH)
    suspend fun getMoves():MainModel
}