package com.ldev.kinoonline.feature.base

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

@SuppressLint("InlinedApi")
fun hideSystemUI(window: Window, view: View) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(window, view).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

@SuppressLint("InlinedApi")
fun showSystemUI(window: Window, view: View) {
    WindowCompat.setDecorFitsSystemWindows(window, true)
    WindowInsetsControllerCompat(window, view)
        .show(WindowInsetsCompat.Type.systemBars())
}

fun <T> Context.isServiceRunning(serviceClass: Class<T>): Boolean {
    if (ContextCompat.getSystemService(this, serviceClass) != null) {
        return true
    }
    return false
}