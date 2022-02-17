package io.customer.remotehabits.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect

@Composable
fun TrackScreenDisposableEffect(
    key: Any? = Unit,
    onScreenEnter: () -> Unit = {},
    onScreenExit: () -> Unit = {}
) {
    DisposableEffect(key) {
        onScreenEnter.invoke()
        onDispose { onScreenExit.invoke() }
    }
}
