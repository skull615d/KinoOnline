package com.ldev.kinoonline.feature.player.service.notifications

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.ldev.kinoonline.MainActivity

class PlayerNotificationActivity : AppCompatActivity() {
    private val KEY = "key"
    private val MOVIE = "movie"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtras(bundleOf(Pair(KEY, MOVIE)))
        startActivity(intent)
    }
}