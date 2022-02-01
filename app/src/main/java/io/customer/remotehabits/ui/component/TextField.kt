package io.customer.remotehabits.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import io.customer.remotehabits.ui.theme.RHTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RHTextField(
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
