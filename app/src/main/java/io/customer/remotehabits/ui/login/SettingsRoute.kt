package io.customer.remotehabits.ui.login

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.customer.remotehabits.R
import io.customer.remotehabits.ui.component.BackNavigationWithTitle
import io.customer.remotehabits.ui.component.RHButton
import io.customer.remotehabits.ui.component.RHTextField
import io.customer.remotehabits.ui.theme.RHTheme

@Composable
fun SettingsRoute(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {
    val trackApiUrlState = loginViewModel.trackApiUrl.collectAsState()
    val context = LocalContext.current

    SettingsScreen(
        trackApiUrl = trackApiUrlState.value,
        updateTrackUrl = { url ->
            url?.let {
                loginViewModel.updateTrackApiUrl(
                    url = it,
                    application = context.applicationContext as Application,
                    onComplete = onBackPressed
                )
            }
        },
        onBackPressed = onBackPressed
    )
}

@Composable
fun SettingsScreen(
    trackApiUrl: String?,
    updateTrackUrl: (String?) -> Unit,
    onBackPressed: () -> Unit
) {
    var trackApiText by remember { mutableStateOf(trackApiUrl) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
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
            title = stringResource(id = R.string.settings),
            onBackPressed = onBackPressed
        )

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
                    value = trackApiText ?: "",
                    onValueChange = {
                        trackApiText = it
                    },
                    label = stringResource(R.string.tracking_api_url)
                )

                RHButton(
                    onClick = {
                        updateTrackUrl(trackApiText)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.update),
                        style = RHTheme.typography.button
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SettingsPreview() {
    SettingsScreen(
        trackApiUrl = "",
        updateTrackUrl = {
        },
        onBackPressed = {
        }
    )
}
