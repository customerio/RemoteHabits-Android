package io.customer.remotehabits.ui.home

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.customer.remotehabits.R
import io.customer.remotehabits.data.models.User
import io.customer.remotehabits.ui.component.BackNavigationWithTitle
import io.customer.remotehabits.ui.component.RHTextField
import io.customer.remotehabits.ui.theme.RHTheme
import io.customer.remotehabits.utils.AnalyticsConstants.SCREEN_SWITCH_WORKSPACE
import io.customer.remotehabits.utils.TrackScreenDisposableEffect

@Composable
fun SwitchWorkspaceRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    workspaceSiteId: String? = null,
    workspaceApiKey: String? = null,
    onBackPressed: () -> Unit,
    onWorkspaceChanged: () -> Unit
) {
    val context = LocalContext.current
    val state = homeViewModel.uiState.collectAsState()

    TrackScreenDisposableEffect(
        onScreenEnter = { homeViewModel.trackScreenEnterEventsName(SCREEN_SWITCH_WORKSPACE) }
    )

    val workSpaceCredentialsState = homeViewModel.workspaceCredentials.collectAsState()
    val wsSiteId = workspaceSiteId ?: workSpaceCredentialsState.value.siteId
    val wsApiKey = workspaceApiKey ?: workSpaceCredentialsState.value.apiKey

    SwitchWorkspaceScreen(
        state = state.value,
        workspaceSiteId = wsSiteId,
        workspaceApiKey = wsApiKey,
        onBackPressed = onBackPressed
    ) { siteId, apiKey, user ->
        homeViewModel.changeWorkspace(
            user = user,
            siteId = siteId,
            apiKey = apiKey,
            appContext = context.applicationContext as Application,
            onWorkspaceChanged = onWorkspaceChanged
        )
    }
}

@Composable
fun SwitchWorkspaceScreen(
    state: HomeUiState,
    workspaceSiteId: String,
    workspaceApiKey: String,
    onBackPressed: () -> Unit,
    onWorkspaceChanged: (siteId: String, apiKey: String, user: User) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        RHTheme.colors.background,
                        RHTheme.colors.backgroundVariant
                    )
                )
            )
            .fillMaxSize()
    ) {
        BackNavigationWithTitle(
            title = stringResource(id = R.string.customer_io),
            onBackPressed = onBackPressed
        )
        SwitchWorkspaceHeader()
        SwitchWorkspaceFormView(
            workspaceApiKey = workspaceApiKey,
            workspaceSiteId = workspaceSiteId
        ) { siteId, apiKey ->
            onWorkspaceChanged.invoke(siteId, apiKey, state.user)
        }
    }
}

@Composable
fun SwitchWorkspaceHeader() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_cio_large),
            contentDescription = stringResource(id = R.string.app_name)
        )
        Text(
            text = stringResource(id = R.string.customer_io_workspace),
            style = RHTheme.typography.body,
            color = RHTheme.colors.textPrimary,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Composable
fun SwitchWorkspaceFormView(
    workspaceSiteId: String,
    workspaceApiKey: String,
    onWorkspaceChanged: (siteId: String, apiKey: String) -> Unit
) {

    var siteId by remember {
        mutableStateOf(
            workspaceSiteId
        )
    }
    var apiKey by remember {
        mutableStateOf(
            workspaceApiKey
        )
    }

    Card(
        modifier = Modifier.padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RHTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                value = siteId,
                onValueChange = {
                    siteId = it
                },
                label = stringResource(R.string.site_id)
            )
            RHTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                value = apiKey,
                onValueChange = {
                    apiKey = it
                },
                label = stringResource(R.string.api_key),
            )
            Button(
                onClick = {
                    onWorkspaceChanged.invoke(siteId, apiKey)
                },
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = RHTheme.colors.primary,
                    contentColor = RHTheme.colors.formBackground
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.switch_workspace),
                    style = RHTheme.typography.button
                )
            }
        }
    }
}

@Preview
@Composable
fun SwitchWorkspacePreview() {
    SwitchWorkspaceScreen(
        state = HomeUiState(),
        onBackPressed = {},
        workspaceApiKey = "",
        workspaceSiteId = "",
        onWorkspaceChanged = { _, _, _ ->
        }
    )
}
