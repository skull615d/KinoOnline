package com.ldev.kinoonline.feature.player.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class BackgroundService : Service() {

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    private var notificationId = 123
    private var channelId = "channelId"

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


}