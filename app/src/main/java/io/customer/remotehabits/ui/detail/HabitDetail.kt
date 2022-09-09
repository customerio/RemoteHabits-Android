package io.customer.remotehabits.ui.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.customer.remotehabits.R
import io.customer.remotehabits.data.models.Habit
import io.customer.remotehabits.data.models.HabitTimeFormat
import io.customer.remotehabits.data.stubs.HabitsStub
import io.customer.remotehabits.extensions.getFormattedTime
import io.customer.remotehabits.ui.component.BackNavigationWithTitle
import io.customer.remotehabits.ui.component.RHBasicTextField
import io.customer.remotehabits.ui.component.RemoteHabitCard
import io.customer.remotehabits.ui.home.HabitListStatusSwitch
import io.customer.remotehabits.ui.theme.RHTheme
import io.customer.remotehabits.utils.AnalyticsConstants.SCREEN_HABIT_DETAIL
import io.customer.remotehabits.utils.TrackScreenDisposableEffect
import java.util.*

@Composable
fun HabitDetailRoute(
    habitDetailViewModel: HabitDetailViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val state = habitDetailViewModel.uiState.collectAsState()

    TrackScreenDisposableEffect(
        onScreenEnter = {
            habitDetailViewModel.trackScreenEnterEventsName(SCREEN_HABIT_DETAIL)
        }
    )

    HabitDetailScreen(
        state = state.value,
        onHabitStatusChange = { habit, isChecked ->
            habitDetailViewModel.onStateChanged(habit, isChecked)
        },
        onBackPressed = onBackPressed,
        onHabitReminderCountUpdate = { habit, count ->
            habitDetailViewModel.onReminderCountChanged(habit, count)
        },
        onHabitStartTimeUpdate = { habit, time ->
            habitDetailViewModel.onReminderStartTimeChanged(habit, time)
        },
        onHabitEndTimeUpdate = { habit, time ->
            habitDetailViewModel.onReminderEndTimeChanged(habit, time)
        }
    )
}

@Composable
fun HabitDetailScreen(
    state: HabitDetailUiState,
    onBackPressed: () -> Unit,
    onHabitStatusChange: (habit: Habit, isChecked: Boolean) -> Unit,
    onHabitReminderCountUpdate: (habit: Habit, count: Int) -> Unit,
    onHabitStartTimeUpdate: (habit: Habit, time: Long) -> Unit,
    onHabitEndTimeUpdate: (habit: Habit, time: Long) -> Unit
) {
    Column(
        modifier = Modifier
            .background(RHTheme.colors.background)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        state.habit?.let { habit ->
            BackNavigationWithTitle(title = habit.name, onBackPressed = onBackPressed)
            HabitDetailIcon(icon = habit.type.getIcon(), caption = habit.caption)
            HabitDetailStatus(
                habit = habit,
                onHabitStatusChange = onHabitStatusChange
            )
            AnimatedVisibility(visible = habit.status) {
                HabitDetailReminder(
                    habit = habit,
                    onHabitReminderCountUpdate = onHabitReminderCountUpdate,
                    onHabitEndTimeUpdate = onHabitEndTimeUpdate,
                    onHabitStartTimeUpdate = onHabitStartTimeUpdate
                )
            }
            HabitDetailView(
                habit = habit
            )
        }
    }
}

@Composable
fun HabitDetailView(habit: Habit) {
    RemoteHabitCard(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.why_it_matters),
                style = RHTheme.typography.h3,
                color = RHTheme.colors.textPrimary
            )

            Text(
                text = habit.description,
                style = RHTheme.typography.body,
                color = RHTheme.colors.textSecondary
            )
        }
    }
}

@Composable
fun ReminderListItem(
    title: String,
    textFieldText: Long?,
    onItemUpdate: (String) -> Unit = {},
    isTimePicker: Boolean = false,
    showTimePicket: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = RHTheme.typography.body,
            color = RHTheme.colors.textSecondary,
            modifier = Modifier.weight(2F)
        )

        if (isTimePicker) {
            ReminderTimeBox(
                modifier = Modifier
                    .weight(1F)
                    .clickable {
                        showTimePicket.invoke()
                    }
            ) {
                val time = if (textFieldText == null) {
                    HabitTimeFormat.defaultTimePlaceHolder
                } else {
                    Date(textFieldText).getFormattedTime()
                }
                Text(
                    text = time,
                    style = RHTheme.typography.input,
                    color = RHTheme.colors.textSecondary
                )
            }
        } else {
            RHBasicTextField(
                value = textFieldText.toString(),
                onChange = {
                    onItemUpdate.invoke(it)
                },
                singleLine = true,
                modifier = Modifier.weight(1F),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}

@Composable
fun HabitDetailStatus(
    habit: Habit,
    onHabitStatusChange: (habit: Habit, isChecked: Boolean) -> Unit
) {
    RemoteHabitCard(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(16.dp)
        ) {
            val displayText = if (habit.status) {
                stringResource(id = R.string.added_to_my_habits)
            } else {
                stringResource(id = R.string.add_to_my_habits)
            }

            Text(
                text = displayText,
                style = RHTheme.typography.h3,
                color = RHTheme.colors.textPrimary
            )

            HabitListStatusSwitch(
                habit = habit,
                onHabitStatusChange = onHabitStatusChange
            )
        }
    }
}

@Composable
fun HabitDetailIcon(icon: Int, caption: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = stringResource(id = R.string.back),
            modifier = Modifier.size(80.dp)
        )
        Text(
            text = caption,
            style = RHTheme.typography.body,
            color = RHTheme.colors.textPrimary
        )
    }
}

@Preview
@Composable
fun HabitDetailPreview() {
    val context = LocalContext.current

    HabitDetailScreen(
        HabitDetailUiState(
            loading = false,
            habit = HabitsStub.getHabits(context).first()
        ),
        onBackPressed = {},
        onHabitStartTimeUpdate = { _, _ ->
        },
        onHabitEndTimeUpdate = { _, _ ->
        },
        onHabitReminderCountUpdate = { _, _ ->
        },
        onHabitStatusChange = { _, _ ->
        }
    )
}
