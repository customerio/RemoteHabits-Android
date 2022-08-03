package io.customer.remotehabits.ui.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.customer.remotehabits.R
import io.customer.remotehabits.ui.component.RemoteHabitCard
import io.customer.remotehabits.ui.theme.RHTheme

@Composable
fun HomeUserDetailTitle() {
    Text(
        text = stringResource(R.string.details),
        color = RHTheme.colors.textPrimary,
        style = RHTheme.typography.h2,
        modifier = Modifier.padding(start = 16.dp, top = 40.dp, bottom = 8.dp, end = 16.dp)
    )
}

@Composable
fun HomeUserDetailItem(
    primaryText: String,
    secondaryText: String,
    @DrawableRes icon: Int,
    @StringRes actionText: Int?,
    onAction: () -> Unit
) {
    RemoteHabitCard(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HabitListIcon(
                painter = painterResource(id = icon),
                contentDescription = primaryText
            )
            HabitListMultiText(
                primaryText = {
                    Text(
                        text = primaryText,
                        color = RHTheme.colors.textTertiary,
                        style = RHTheme.typography.body
                    )
                },
                secondaryText = {
                    Text(
                        text = secondaryText,
                        color = RHTheme.colors.textPrimary,
                        style = RHTheme.typography.h3
                    )
                }
            )
            HabitListActionText(
                actionText = if (actionText == null) "" else stringResource(id = actionText),
                onAction = onAction
            )
        }
    }
}
