package org.yarokovisty.delivery.common.validation.validator

import org.yarokovisty.delivery.common.validation.error.AddressValidationError
import org.yarokovisty.delivery.util.validation.validated.invalid
import org.yarokovisty.delivery.util.validation.validated.valid
import kotlin.test.Test
import kotlin.test.assertEquals

class AddressValidatorTest {

    private val validator = AddressValidator()

    @Test
    fun `validate with valid address EXPECT valid result`() {
        val expected = valid("123 Main Street")
        val address = "123 Main Street"

        val actual = validator.validate(address)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with single character address EXPECT valid result`() {
        val expected = valid("A")
        val address = "A"

        val actual = validator.validate(address)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with empty address EXPECT invalid with empty error`() {
        val expected = invalid(AddressValidationError.EMPTY)
        val address = ""

        val actual = validator.validate(address)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with blank address EXPECT invalid with empty error`() {
        val expected = invalid(AddressValidationError.EMPTY)
        val address = "   "

        val actual = validator.validate(address)

        assertEquals(expected, actual)
    }
}
