package io.customer.remotehabits.extensions

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity

/**
 * Find the closest Activity in a given Context.
 */
internal fun Context.findActivity(): AppCompatActivity {
    var context = this
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Method should be called in context of an Activity")
}
