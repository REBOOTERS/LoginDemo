package com.engineer.android.mini.ext

import android.content.Context

fun Context?.getStatusBarHeight(): Int {
    var result = 0
    this?.let {
        val resourceId = it.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = it.resources.getDimensionPixelSize(resourceId)
        }
    }
    return result
}