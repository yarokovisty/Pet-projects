package org.yarokovisty.delivery.design.uikit

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.ui.text.TextRange

/**
 * Input transformation for Russian phone numbers.
 *
 * Format: +7 999 999 99 99
 *
 * Features:
 * - Normalizes input: 8→7 conversion, keeps 7, prepends 7 for other digits
 * - Limits to 11 digits (including country code)
 * - Automatically formats with spaces in correct positions
 * - Preserves cursor position during editing, backspace, and paste operations
 * - No infinite recomposition loops or state desync
 */
class PhoneInputTransformation : InputTransformation {

    private companion object {
        const val MAX_DIGITS = 11
        const val COUNTRY_CODE = '7'
        const val OLD_COUNTRY_CODE = '8'

        const val SPACE_AFTER_COUNTRY_CODE = 1
        const val SPACE_AFTER_FIRST_GROUP = 4
        const val SPACE_AFTER_SECOND_GROUP = 7
        const val SPACE_AFTER_THIRD_GROUP = 9
    }

    override fun TextFieldBuffer.transformInput() {
        val currentText = asCharSequence().toString()
        val currentCursor = selection.start

        val inputDigits = currentText.filter { it.isDigit() }

        val normalizedDigits = normalizeDigits(inputDigits)

        val limitedDigits = normalizedDigits.take(MAX_DIGITS)

        val formattedText = formatPhoneNumber(limitedDigits)

        var digitsBeforeCursor = currentText
            .take(currentCursor)
            .count { it.isDigit() }

        if (inputDigits.isNotEmpty() &&
            inputDigits.first() != COUNTRY_CODE &&
            inputDigits.first() != OLD_COUNTRY_CODE
        ) {
            digitsBeforeCursor++
        }

        val newCursor = findPositionAfterNDigits(formattedText, digitsBeforeCursor)

        replace(0, length, formattedText)

        selection = TextRange(newCursor.coerceIn(0, formattedText.length))
    }
    private fun normalizeDigits(digits: String): String {
        if (digits.isEmpty()) return ""

        return when (digits.first()) {
            OLD_COUNTRY_CODE -> COUNTRY_CODE + digits.substring(1)
            COUNTRY_CODE -> digits
            else -> COUNTRY_CODE + digits
        }
    }

    private fun formatPhoneNumber(digits: String): String {
        if (digits.isEmpty()) return ""

        val result = StringBuilder("+")

        digits.forEachIndexed { index, digit ->
            when (index) {
                0 -> result.append(digit)
                SPACE_AFTER_COUNTRY_CODE,
                SPACE_AFTER_FIRST_GROUP,
                SPACE_AFTER_SECOND_GROUP,
                SPACE_AFTER_THIRD_GROUP -> result.append(" ").append(digit)
                else -> result.append(digit)
            }
        }

        return result.toString()
    }

    private fun findPositionAfterNDigits(text: String, n: Int): Int {
        if (n == 0) return 0

        var digitCount = 0
        var position = text.length

        for (i in text.indices) {
            if (text[i].isDigit()) {
                digitCount++
                if (digitCount == n) {
                    position = i + 1
                    break
                }
            }
        }

        return position
    }
}
