package io.customer.remotehabits.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.customer.remotehabits.data.models.Habit
import io.customer.remotehabits.ui.component.RemoteHabitCard
import io.customer.remotehabits.ui.theme.RHTheme

@Composable
fun HomeHabitListItem(
    habit: Habit,
    openHabitDetail: (habit: Habit) -> Unit,
    onHabitStatusChange: (habit: Habit, isChecked: Boolean) -> Unit
) {
    RemoteHabitCard(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable {
                openHabitDetail.invoke(habit)
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HabitListIcon(
                painter = painterResource(id = habit.type.getIcon()),
                contentDescription = habit.name
            )
            HabitListMultiText(
                primaryText = {
                    Text(
                        text = habit.name,
                        color = RHTheme.colors.textPrimary,
                        style = RHTheme.typography.h3
                    )
                },
                secondaryText = {
                    Text(
                        text = habit.caption,
                        color = RHTheme.colors.textTertiary,
                        style = RHTheme.typography.body
                    )
                }
            )
            HabitListStatusSwitch(
                modifier = Modifier.weight(1F),
                habit = habit,
                onHabitStatusChange = onHabitStatusChange
            )
        }
    }
}

@Composable
fun HabitListStatusSwitch(
    modifier: Modifier = Modifier,
    habit: Habit,
    onHabitStatusChange: (habit: Habit, isChecked: Boolean) -> Unit
) {
    Switch(
        checked = habit.status,
        modifier = modifier,
        colors = SwitchDefaults.colors(
            checkedThumbColor = RHTheme.colors.primary,
            uncheckedThumbColor = RHTheme.colors.formBackground,
            checkedTrackColor = RHTheme.colors.accent,
            uncheckedTrackColor = RHTheme.colors.underline
        ),
        onCheckedChange = {
            onHabitStatusChange.invoke(habit, it)
        }
    )
}

@Composable
fun RowScope.HabitListMultiText(
    primaryText: @Composable () -> Unit,
    secondaryText: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.weight(3F)
    ) {
        primaryText()
        secondaryText()
    }
}

@Composable
fun RowScope.HabitListActionText(
    actionText: String,
    onAction: () -> Unit
) {
    Text(
        text = actionText,
        style = RHTheme.typography.button,
        color = RHTheme.colors.accent,
        modifier = Modifier
            .weight(1F)
            .clickable {
                onAction.invoke()
            }
    )
}

@Composable
fun RowScope.HabitListIcon(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier.weight(1F)
    )
}
