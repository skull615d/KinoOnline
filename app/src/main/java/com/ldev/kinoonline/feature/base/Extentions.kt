package com.ldev.kinoonline.feature.base

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.ldev.kinoonline.R
import com.ldev.kinoonline.feature.base.constants.Constants
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.loadImage(
    src: String?,
    @DrawableRes errorRes: Int = R.drawable.ic_non_existing_url,
    @DrawableRes placeholderRes: Int = R.drawable.ic_non_existing_url,
    config: RequestBuilder<Drawable>.() -> Unit = {}
) {
    Glide
        .with(context)
        .load(src)
        .error(errorRes)
        .placeholder(placeholderRes)
        .apply { config(this) }
        .into(this)
}

fun String.toCalendar(dateFormat: String = "yyyy-MM-dd"): Calendar? {
    val calendar = Calendar.getInstance()
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.parse(this)?.let { calendar.time = it }
    if (calendar == Calendar.getInstance()) return null
    return calendar
}

fun Calendar?.toStringFormat(format: String = "dd.MM.yyyy HH:mm"): String {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return this?.time.let { formatter.format(it) }
}

fun <T> ListDelegationAdapter<List<T>>.updateList(list: List<T>) {
    this.items = list
    this.notifyDataSetChanged()
}

fun View.setThrottledClickListener(
    delay: Long = Constants.DEFAULT_THROTTLE_DELAY,
    onClick: (View) -> Unit
) {
    setOnClickListener {
        throttle(delay) {
            onClick(it)
        }
    }
}

private var lastClickTimestamp = 0L
fun throttle(delay: Long = Constants.DEFAULT_THROTTLE_DELAY, action: () -> Unit): Boolean {
    val currentTimestamp = System.currentTimeMillis()
    val delta = currentTimestamp - lastClickTimestamp
    if (delta !in 0L..delay) {
        lastClickTimestamp = currentTimestamp
        action()
        return true
    }
    return false
}

