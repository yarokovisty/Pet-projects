package org.yarokovisty.delivery.design.uikit.input

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.FocusedBorderThickness
import androidx.compose.material3.OutlinedTextFieldDefaults.UnfocusedBorderThickness
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.VerticalGap
import org.yarokovisty.delivery.design.uikit.text.Paragraph14Regular
import org.yarokovisty.delivery.design.uikit.text.Paragraph16Regular

@Suppress("LongMethod")
@Composable
fun TextInput(
    text: String,
    onTextChange: (String) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = remember { KeyboardOptions.Default },
    focusRequester: FocusRequester = remember { FocusRequester() },
    colors: TextFieldColors = defaultInputColors(),
    shape: Shape = RoundedCornerShape(8.dp),
    focusedBorderWidth: Dp = FocusedBorderThickness,
    unfocusedBorderWidth: Dp = UnfocusedBorderThickness,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    title: String? = null,
    hint: String? = null,
    errorText: String? = null,
    inputTransformation: InputTransformation? = null,
    onFocusChange: ((hasFocus: Boolean) -> Unit)? = null,
    onImeAction: ((ImeAction) -> Unit)? = null,
) {
    val state = rememberTextFieldState(text)
    val errorEnabled = errorText != null
    val lineLimits = remember(singleLine) {
        when {
            singleLine -> TextFieldLineLimits.SingleLine
            else -> TextFieldLineLimits.MultiLine(
                minHeightInLines = minLines,
                maxHeightInLines = maxLines
            )
        }
    }
    val heightModifier = if (singleLine) {
        Modifier.height(48.dp)
    } else {
        Modifier.heightIn(min = 48.dp)
    }

    LaunchedEffect(text) {
        if (state.text.toString() != text) {
            state.setTextAndPlaceCursorAtEnd(text)
        }
    }

    LaunchedEffect(state) {
        snapshotFlow { state.text.toString() }
            .collectLatest(onTextChange)
    }

    Column(modifier = modifier) {
        UpperTextContainer(title)

        BasicTextField(
            state = state,
            enabled = enabled,
            keyboardOptions = keyboardOptions,
            inputTransformation = inputTransformation,
            interactionSource = interactionSource,
            textStyle = getInputTextStyle(enabled, errorEnabled),
            cursorBrush = getCursorColor(errorEnabled),
            lineLimits = lineLimits,
            modifier = Modifier
                .fillMaxWidth()
                .then(heightModifier)
                .focusRequester(focusRequester)
                .onFocusChanged { onFocusChange?.invoke(it.hasFocus) },
            onKeyboardAction = { onImeAction?.invoke(keyboardOptions.imeAction) },
            decorator = { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = state.text.toString(),
                    enabled = enabled,
                    singleLine = singleLine,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    isError = errorEnabled,
                    placeholder = {
                        if (hint != null) {
                            Placeholder(text = hint)
                        }
                    },
                    innerTextField = { innerTextField() },
                    contentPadding = PaddingValues(12.dp),
                    container = {
                        OutlinedTextFieldDefaults.Container(
                            enabled = enabled,
                            isError = errorEnabled,
                            interactionSource = interactionSource,
                            colors = colors,
                            shape = shape,
                            focusedBorderThickness = focusedBorderWidth,
                            unfocusedBorderThickness = if (errorEnabled) {
                                focusedBorderWidth
                            } else {
                                unfocusedBorderWidth
                            }
                        )
                    }
                )
            }
        )

        BottomTextContainer(errorText)
    }
}

@Composable
private fun Placeholder(text: String) {
    Paragraph16Regular(
        text = text,
        color = DeliveryTheme.colorScheme.textTertiary
    )
}

@Composable
private fun UpperTextContainer(
    title: String?
) {
    Column {
        if (title != null) {
            Paragraph14Regular(text = title)
        }

        VerticalGap(6.dp)
    }
}

@Composable
private fun BottomTextContainer(
    errorText: String?
) {
    Column {
        VerticalGap(4.dp)

        if (errorText != null) {
            ErrorText(errorText)
        }
    }
}

@Composable
private fun ErrorText(text: String) {
    Paragraph14Regular(
        text = text,
        color = DeliveryTheme.colorScheme.textError
    )
}

@Composable
private fun getInputTextStyle(
    enabled: Boolean,
    errorEnabled: Boolean
): TextStyle =
    DeliveryTheme.typography.paragraph16Regular.copy(
        color = when {
            !enabled -> DeliveryTheme.colorScheme.textTertiary
            errorEnabled -> DeliveryTheme.colorScheme.textPrimary
            else -> DeliveryTheme.colorScheme.textPrimary
        }
    )

@Composable
private fun getCursorColor(
    errorEnabled: Boolean
): Brush =
    SolidColor(
        value = if (errorEnabled) {
            DeliveryTheme.colorScheme.indicatorError
        } else {
            DeliveryTheme.colorScheme.brandIndicator
        }
    )

@Composable
private fun defaultInputColors(): TextFieldColors =
    OutlinedTextFieldDefaults.colors(
        focusedTextColor = DeliveryTheme.colorScheme.textPrimary,
        unfocusedTextColor = DeliveryTheme.colorScheme.textPrimary,
        disabledTextColor = DeliveryTheme.colorScheme.textTertiary,
        errorTextColor = DeliveryTheme.colorScheme.textPrimary,
        focusedContainerColor = DeliveryTheme.colorScheme.bgPrimary,
        unfocusedContainerColor = DeliveryTheme.colorScheme.bgPrimary,
        errorContainerColor = DeliveryTheme.colorScheme.bgPrimary,
        disabledContainerColor = DeliveryTheme.colorScheme.bgSecondary,
        cursorColor = DeliveryTheme.colorScheme.brandIndicator,
        errorCursorColor = DeliveryTheme.colorScheme.indicatorError,
        focusedBorderColor = DeliveryTheme.colorScheme.brandIndicator,
        unfocusedBorderColor = DeliveryTheme.colorScheme.borderLight,
        disabledBorderColor = DeliveryTheme.colorScheme.borderLight,
        errorBorderColor = DeliveryTheme.colorScheme.indicatorError,
    )

@Preview(showBackground = true)
@Composable
private fun TextInputPreview() {
    DeliveryTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextInput(
                text = "",
                onTextChange = {},
                title = "Title",
                hint = "Hint",
                modifier = Modifier.fillMaxWidth(),
            )

            TextInput(
                text = "",
                onTextChange = {},
                title = "Title",
                hint = "Hint",
                errorText = "Error",
                modifier = Modifier.fillMaxWidth(),
            )

            TextInput(
                text = "Value",
                onTextChange = {},
                title = "Title",
                hint = "Hint",
                modifier = Modifier.fillMaxWidth(),
            )

            TextInput(
                text = "Value",
                onTextChange = {},
                title = "Title",
                hint = "Hint",
                errorText = "Error",
                modifier = Modifier.fillMaxWidth(),
            )

            TextInput(
                text = "",
                onTextChange = {},
                title = "Title",
                hint = "Hint",
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
            )

            TextInput(
                text = "Value",
                onTextChange = {},
                title = "Title",
                hint = "Hint",
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
