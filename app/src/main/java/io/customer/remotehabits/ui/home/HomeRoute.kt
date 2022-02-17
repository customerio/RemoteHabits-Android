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
import io.customer.remotehabits.utils.AnalyticsConstants.SCREEN_DASHBOARD
import io.customer.remotehabits.utils.TrackScreenDisposableEffect
import io.customer.sdk.CustomerIO

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    openHabitDetail: (habit: Habit) -> Unit,
    switchWorkspace: () -> Unit,
    onLogout: () -> Unit
) {
    val state = homeViewModel.uiState.collectAsState()

    TrackScreenDisposableEffect(
        onScreenEnter = {
            homeViewModel.trackScreenEnterEventsName(SCREEN_DASHBOARD)
        }
    )

    val context = LocalContext.current
    HomeScreen(
        state = state.value,
        openHabitDetail = openHabitDetail,
        onHabitStatusChange = { type, isChecked ->
            homeViewModel.onStateChanged(type, isChecked)
        },
        switchWorkspace = switchWorkspace,
        onLogout = { user ->
            homeViewModel.logout(user, context, onLogout)
        }
    )
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    openHabitDetail: (habit: Habit) -> Unit,
    onHabitStatusChange: (habit: Habit, isChecked: Boolean) -> Unit,
    switchWorkspace: () -> Unit,
    onLogout: (user: User) -> Unit
) {
    Column(
        Modifier
            .background(RHTheme.colors.background)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        HomeScreenTitle(state.user)
        HomeScreenHabits(state.habits, openHabitDetail, onHabitStatusChange)
        HomeScreenUserDetails(state.user, switchWorkspace = switchWorkspace, onLogout = onLogout)
    }
}

@Composable
fun HomeScreenUserDetails(
    user: User,
    switchWorkspace: () -> Unit,
    onLogout: (user: User) -> Unit,

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
            switchWorkspace.invoke()
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
    openHabitDetail: (habit: Habit) -> Unit,
    onHabitStatusChange: (habit: Habit, isChecked: Boolean) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        habits.forEach { habit ->
            HomeHabitListItem(
                habit = habit,
                openHabitDetail = openHabitDetail,
                onHabitStatusChange = onHabitStatusChange
            )
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
        openHabitDetail = {},
        onHabitStatusChange = { _, _ -> },
        switchWorkspace = {},
        onLogout = { }
    )
}
