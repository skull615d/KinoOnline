package com.ldev.kinoonline.feature.player.di

import com.google.android.exoplayer2.SimpleExoPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val modulePlayer = module {
    single {
        SimpleExoPlayer.Builder(androidContext())
            .build()
    }
}