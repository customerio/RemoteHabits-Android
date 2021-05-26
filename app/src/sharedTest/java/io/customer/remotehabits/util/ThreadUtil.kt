package io.customer.remotehabits.util

import android.os.Looper

object ThreadUtil {

    val isMainThread: Boolean
        get() = Looper.myLooper() == Looper.getMainLooper()

    val isBackgroundThread: Boolean
        get() = !isMainThread
}
