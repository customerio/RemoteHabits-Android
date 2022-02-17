package io.customer.remotehabits.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import io.customer.remotehabits.ui.detail.ReminderTimeBox
import io.customer.remotehabits.ui.login.loginTextFieldColors
import io.customer.remotehabits.ui.theme.RHTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RHBasicTextField(
    modifier: Modifier = Modifier,
    value: String,
    onChange: (String) -> Unit = {},
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = RHTheme.typography.input.copy(color = RHTheme.colors.textSecondary),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions? = null,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    cursorBrush: Brush = SolidColor(RHTheme.colors.textSecondary),
    decorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit = { innerTextField ->
        ReminderTimeBox {
            innerTextField()
        }
    }
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val actions = keyboardActions ?: KeyboardActions(onDone = {
        keyboardController?.hide()
    })

    BasicTextField(
        value = value,
        onValueChange = onChange,
        cursorBrush = cursorBrush,
        textStyle = textStyle,
        singleLine = singleLine,
        enabled = enabled,
        readOnly = readOnly,
        modifier = modifier,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        visualTransformation = visualTransformation,
        keyboardActions = actions,
        keyboardOptions = keyboardOptions,
        decorationBox = decorationBox
    )
}

@Composable
fun RHTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    errorMessage: String = ""
) {
    TextField(
        value = value,
        textStyle = RHTheme.typography.input,
        colors = loginTextFieldColors(),
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                color = RHTheme.colors.textTertiary,
                style = RHTheme.typography.caption
            )
        },
        keyboardOptions = keyboardOptions,
        isError = errorMessage.isNotEmpty(),
        modifier = modifier,
    )
    if (errorMessage.isNotEmpty()) {
        Text(
            text = errorMessage,
            color = RHTheme.colors.error,
            style = RHTheme.typography.caption
        )
    }
}
