package io.customer.remotehabits.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.customer.remotehabits.ui.theme.RHTheme

@Composable
fun RemoteHabitCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        backgroundColor = RHTheme.colors.cardBackground,
        shape = RoundedCornerShape(size = 12.dp),
        border = BorderStroke(width = 1.dp, color = RHTheme.colors.surface),
        modifier = modifier
    ) {
        content()
    }
}
