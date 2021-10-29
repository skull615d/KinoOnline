package com.ldev.kinoonline.feature.main_screen.data.api

import com.ldev.kinoonline.feature.main_screen.data.api.model.MainModel
import retrofit2.http.GET

interface MainScreenApi {
    @GET("LukyanovAnatoliy/eca5141dedc79751b3d0b339649e06a3/raw/38f9419762adf27c34a3f052733b296385b6aa8d/")
    suspend fun getMoves():MainModel
}