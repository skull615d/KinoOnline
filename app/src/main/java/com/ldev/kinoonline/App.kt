package com.ldev.kinoonline

import android.app.Application
import com.ldev.kinoonline.feature.di.appModule
import com.ldev.kinoonline.feature.main_screen.di.maiScreenModule
import com.ldev.kinoonline.feature.movie_card.di.moduleMovieCard
import com.ldev.kinoonline.feature.player.di.modulePlayer
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule, maiScreenModule, moduleMovieCard, modulePlayer)
        }

    }
}