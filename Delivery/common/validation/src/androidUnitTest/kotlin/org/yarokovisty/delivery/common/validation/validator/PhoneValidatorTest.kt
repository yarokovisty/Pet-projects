package org.yarokovisty.delivery.common.validation.validator

import org.yarokovisty.delivery.common.validation.error.PhoneValidationError
import org.yarokovisty.delivery.util.validation.validated.invalid
import org.yarokovisty.delivery.util.validation.validated.valid
import kotlin.test.Test
import kotlin.test.assertEquals

class PhoneValidatorTest {

    private val validator = PhoneValidator()

    @Test
    fun `validate with valid phone and correct length EXPECT valid result`() {
        val expected = valid("1234567890")
        val phone = "1234567890"
        val length = 10

        val actual = validator.validate(phone, length)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with empty phone EXPECT invalid with empty error`() {
        val expected = invalid(PhoneValidationError.EMPTY)
        val phone = ""
        val length = 10

        val actual = validator.validate(phone, length)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with blank phone EXPECT invalid with empty error`() {
        val expected = invalid(PhoneValidationError.EMPTY)
        val phone = "   "
        val length = 10

        val actual = validator.validate(phone, length)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with phone shorter than required length EXPECT invalid with invalid length error`() {
        val expected = invalid(PhoneValidationError.INVALID_LENGTH)
        val phone = "123"
        val length = 10

        val actual = validator.validate(phone, length)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with phone longer than required length EXPECT invalid with invalid length error`() {
        val expected = invalid(PhoneValidationError.INVALID_LENGTH)
        val phone = "12345678901"
        val length = 10

        val actual = validator.validate(phone, length)

        assertEquals(expected, actual)
    }
}
