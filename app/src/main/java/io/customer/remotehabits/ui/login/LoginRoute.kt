package io.customer.remotehabits.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.customer.remotehabits.R
import io.customer.remotehabits.ui.component.RHTextField
import io.customer.remotehabits.ui.theme.RHTheme
import io.customer.remotehabits.utils.AnalyticsConstants.SCREEN_LOGIN
import io.customer.remotehabits.utils.TrackScreenDisposableEffect

@Composable
fun LoginRoute(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onSettingsClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val state = loginViewModel.uiState.collectAsState()

    TrackScreenDisposableEffect(
        onScreenEnter = { loginViewModel.trackScreenName(SCREEN_LOGIN) }
    )

    LoginScreen(
        uiState = state.value,
        onLogin = { email, name ->
            loginViewModel.loginUser(
                email = email,
                name = name,
                onLoginSuccess = onLoginSuccess
            )
        },
        onGuestLogin = {
            loginViewModel.loginAsGuest(onLoginSuccess)
        },
        onSettingsClick = onSettingsClick
    )
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onLogin: (email: String, name: String) -> Unit,
    onSettingsClick: () -> Unit,
    onGuestLogin: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
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
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginSettings(onSettingsClick)
            LoginScreenHeader()
            LoginFormView(
                emailErrorState = if (uiState.emailError == null) "" else stringResource(id = uiState.emailError),
                nameErrorState = if (uiState.nameError == null) "" else stringResource(id = uiState.nameError),
                onLogin = onLogin,
                onGuestLogin = onGuestLogin
            )
        }
        LoginScreenFooter()
    }
}

@Composable
fun ColumnScope.LoginSettings(onSettingsClick: () -> Unit) {
    Icon(
        imageVector = Icons.Default.Settings,
        contentDescription = stringResource(R.string.login_settings),
        modifier = Modifier
            .size(64.dp)
            .align(Alignment.End)
            .padding(16.dp)
            .clickable(onClick = onSettingsClick),
        tint = RHTheme.colors.textSecondary
    )
}

@Composable
fun LoginScreenFooter() {
    Box {
        Image(
            painter = painterResource(R.drawable.ic_login_table),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painter = painterResource(R.drawable.ic_login_globe),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Image(
                painter = painterResource(R.drawable.ic_login_reports),
                contentDescription = null,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
            Image(
                painter = painterResource(R.drawable.ic_login_computer),
                contentDescription = null,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
            Image(
                painter = painterResource(R.drawable.ic_login_cup),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun LoginScreenHeader() {
    Text(
        text = stringResource(R.string.remote_habbits),
        style = RHTheme.typography.h1,
        color = RHTheme.colors.textPrimary,
        modifier = Modifier.padding(top = 74.dp)
    )
}

@Composable
fun loginTextFieldColors(
    textColor: Color = RHTheme.colors.textSecondary,
    backgroundColor: Color = Color.White,
    cursorColor: Color = RHTheme.colors.primary
) = TextFieldDefaults.textFieldColors(
    textColor = textColor,
    backgroundColor = backgroundColor,
    cursorColor = cursorColor
)

@Composable
fun LoginFormView(
    emailErrorState: String,
    nameErrorState: String,
    onLogin: (email: String, name: String) -> Unit,
    onGuestLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 22.dp),
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
                value = name,
                onValueChange = {
                    name = it
                },
                label = stringResource(R.string.name),
                errorMessage = nameErrorState
            )
            RHTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                value = email,
                onValueChange = {
                    email = it
                },
                label = stringResource(R.string.email),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                errorMessage = emailErrorState
            )
            Button(
                onClick = {
                    onLogin.invoke(email, name)
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
                    text = stringResource(R.string.login),
                    style = RHTheme.typography.button
                )
            }
            Text(
                text = stringResource(R.string.continue_as_guest),
                style = RHTheme.typography.button.copy(fontWeight = FontWeight.W500),
                color = RHTheme.colors.primary,
                modifier = Modifier
                    .padding(bottom = 28.dp)
                    .clickable {
                        onGuestLogin.invoke()
                    }
            )
        }
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        LoginUiState(),
        onLogin = { _, _ -> },
        onSettingsClick = {},
        onGuestLogin = {}
    )
}
