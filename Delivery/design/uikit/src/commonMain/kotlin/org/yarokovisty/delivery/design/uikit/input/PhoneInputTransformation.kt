package org.yarokovisty.delivery.design.uikit.input

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.ui.text.TextRange
import org.yarokovisty.delivery.util.phone.PhoneNumberFormatter

/**
 * Input transformation for phone numbers.
 *
 * The specific country format (mask, country code, max digits) is provided
 * via [PhoneNumberFormatter].
 *
 * Features:
 * - Delegates formatting and normalization to [PhoneNumberFormatter]
 * - Preserves cursor position during editing, backspace, and paste operations
 * - No infinite recomposition loops or state desync
 */
class PhoneInputTransformation(
    private val formatter: PhoneNumberFormatter,
) : InputTransformation {

    override fun TextFieldBuffer.transformInput() {
        val currentText = asCharSequence().toString()
        val currentCursor = selection.start

        val formattedText = formatter.format(currentText)

        if (formattedText == currentText) return

        val rawInputDigits = formatter.unformat(currentText)
        val normalizedDigits = formatter.normalize(rawInputDigits)

        var digitsBeforeCursor = currentText
            .take(currentCursor)
            .count { it.isDigit() }

        if (normalizedDigits.length > rawInputDigits.length) {
            digitsBeforeCursor++
        }

        val newCursor = findPositionAfterNDigits(formattedText, digitsBeforeCursor)

        replace(0, length, formattedText)
        selection = TextRange(newCursor.coerceIn(0, formattedText.length))
    }

    private fun findPositionAfterNDigits(text: String, n: Int): Int {
        if (n == 0) return 0

        var digitCount = 0
        val foundIndex = text.indexOfFirst { char ->
            if (char.isDigit()) digitCount++
            digitCount == n
        }

        return if (foundIndex >= 0) foundIndex + 1 else text.length
    }
}
