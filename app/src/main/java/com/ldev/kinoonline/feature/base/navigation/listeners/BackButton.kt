package com.ldev.kinoonline.feature.base.navigation.listeners

class BackButton(private val func: () -> Unit) : BackButtonListener {
    override fun onBackPressed() {
        func()
    }
}