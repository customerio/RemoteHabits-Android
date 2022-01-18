package io.customer.remotehabits.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.customer.remotehabits.R
import io.customer.remotehabits.data.models.Habit
import io.customer.remotehabits.data.models.User
import io.customer.remotehabits.data.stubs.HabitsStub
import io.customer.remotehabits.ui.theme.RHTheme
import io.customer.sdk.CustomerIO

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state = homeViewModel.uiState.collectAsState()
    val context = LocalContext.current
    HomeScreen(
        state = state.value,
        onHabitStatusChange = { type, isChecked ->
            homeViewModel.onStateChanged(type, isChecked)
        },
        onLogout = { user ->
            homeViewModel.logout(user, context)
        }
    )
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    onHabitStatusChange: (habit: Habit, isChecked: Boolean) -> Unit,
    onLogout: (user: User) -> Unit
) {
    Column(
        Modifier
            .background(RHTheme.colors.background)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        HomeScreenTitle(state.user)
        HomeScreenHabits(state.habits, onHabitStatusChange)
        HomeScreenUserDetails(state.user, onLogout = onLogout)
    }
}

@Composable
fun HomeScreenUserDetails(
    user: User,
    onLogout: (user: User) -> Unit
) {
    val userDisplayText: Pair<String, String> = if (user.isGuest)
        Pair(stringResource(id = R.string.guest), stringResource(id = R.string.anonymous))
    else
        Pair(user.email, user.name ?: "")

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        HomeUserDetailTitle()
        HomeUserDetailItem(
            primaryText = userDisplayText.first,
            secondaryText = userDisplayText.second,
            icon = R.drawable.ic_logged_in_user,
            actionText = R.string.logout,
            onAction = {
                onLogout.invoke(user)
            }
        )
        HomeUserDetailItem(
            primaryText = stringResource(id = R.string.site_id),
            secondaryText = CustomerIO.instance().config.siteId,
            icon = R.drawable.ic_cio,
            actionText = R.string.switch_id
        ) {
        }
        HomeUserDetailItem(
            primaryText = stringResource(id = R.string.sdk),
            secondaryText = CustomerIO.instance().store.deviceStore.buildUserAgent(),
            icon = R.drawable.ic_sdk,
            actionText = null
        ) {
        }
    }
}

@Composable
fun HomeScreenHabits(
    habits: List<Habit>,
    onHabitStatusChange: (habit: Habit, isChecked: Boolean) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        habits.forEach { habit ->
            HomeHabitListItem(habit, onHabitStatusChange)
        }
    }
}

@Composable
fun HomeScreenTitle(user: User) {
    user.name?.let {
        Text(
            text = stringResource(id = R.string.users_habits, it),
            style = RHTheme.typography.h2,
            color = RHTheme.colors.textPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 26.dp)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun PreviewHome() {
    val context = LocalContext.current
    HomeScreen(
        HomeUiState(
            user = User(name = "Bradley", email = "brad@email.com"),
            habits = HabitsStub.getHabits(context).toList()
        ),
        onHabitStatusChange = { _, _ -> },
        onLogout = { _ -> }
    )
}