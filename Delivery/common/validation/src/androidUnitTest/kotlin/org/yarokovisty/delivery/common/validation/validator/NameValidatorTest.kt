package org.yarokovisty.delivery.common.validation.validator

import org.yarokovisty.delivery.common.validation.error.NameValidationError
import org.yarokovisty.delivery.util.validation.validated.invalid
import org.yarokovisty.delivery.util.validation.validated.valid
import kotlin.test.Test
import kotlin.test.assertEquals

class NameValidatorTest {

    private val validator = NameValidator()

    @Test
    fun `validate with valid name EXPECT valid result`() {
        val expected = valid("John")
        val name = "John"

        val actual = validator.validate(name)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with name containing spaces EXPECT valid result`() {
        val expected = valid("John Doe")
        val name = "John Doe"

        val actual = validator.validate(name)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with single character name EXPECT valid result`() {
        val expected = valid("A")
        val name = "A"

        val actual = validator.validate(name)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with empty name EXPECT invalid with empty error`() {
        val expected = invalid(NameValidationError.EMPTY)
        val name = ""

        val actual = validator.validate(name)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with blank name EXPECT invalid with empty error`() {
        val expected = invalid(NameValidationError.EMPTY)
        val name = "   "

        val actual = validator.validate(name)

        assertEquals(expected, actual)
    }
}
