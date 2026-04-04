package org.yarokovisty.delivery.feature.login.impl.domain.validator

import org.yarokovisty.delivery.util.validation.validated.fold
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class OtpCodeFormatValidatorTest {

    private val validator = OtpCodeFormatValidator()

    private companion object {
        const val VALID_CODE = "123456"
        const val SHORT_CODE = "12345"
        const val LONG_CODE = "1234567"
        const val EMPTY_CODE = ""
        const val BLANK_CODE = "   "
    }

    @Test
    fun `validate with 6-digit code EXPECT valid result`() {
        val result = validator.validate(VALID_CODE)

        result.fold(
            onValid = { value -> assertEquals(VALID_CODE, value) },
            onInvalid = { fail("Expected valid result") }
        )
    }

    @Test
    fun `validate with empty string EXPECT invalid with empty error`() {
        val result = validator.validate(EMPTY_CODE)

        result.fold(
            onValid = { fail("Expected invalid result") },
            onInvalid = { error -> assertEquals(OtpCodeFormatValidationError.EMPTY, error) }
        )
    }

    @Test
    fun `validate with less than 6 digits EXPECT invalid with invalid length error`() {
        val result = validator.validate(SHORT_CODE)

        result.fold(
            onValid = { fail("Expected invalid result") },
            onInvalid = { error -> assertEquals(OtpCodeFormatValidationError.INVALID_LENGTH, error) }
        )
    }

    @Test
    fun `validate with more than 6 digits EXPECT invalid with invalid length error`() {
        val result = validator.validate(LONG_CODE)

        result.fold(
            onValid = { fail("Expected invalid result") },
            onInvalid = { error -> assertEquals(OtpCodeFormatValidationError.INVALID_LENGTH, error) }
        )
    }

    @Test
    fun `validate with blank string EXPECT invalid with empty error`() {
        val result = validator.validate(BLANK_CODE)

        result.fold(
            onValid = { fail("Expected invalid result") },
            onInvalid = { error -> assertEquals(OtpCodeFormatValidationError.EMPTY, error) }
        )
    }

    @Test
    fun `validate with exactly 6 digits EXPECT valid result with same value`() {
        val testCode = "654321"
        val result = validator.validate(testCode)

        result.fold(
            onValid = { value -> assertEquals(testCode, value) },
            onInvalid = { fail("Expected valid result") }
        )
    }
}
