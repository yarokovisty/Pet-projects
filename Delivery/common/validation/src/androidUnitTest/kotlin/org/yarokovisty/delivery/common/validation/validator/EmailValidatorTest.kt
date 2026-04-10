package org.yarokovisty.delivery.common.validation.validator

import org.yarokovisty.delivery.common.validation.error.EmailValidationError
import org.yarokovisty.delivery.util.validation.validated.invalid
import org.yarokovisty.delivery.util.validation.validated.valid
import kotlin.test.Test
import kotlin.test.assertEquals

class EmailValidatorTest {

    private val validator = EmailValidator()

    @Test
    fun `validate with valid email EXPECT valid result`() {
        val expected = valid("test@example.com")
        val email = "test@example.com"

        val actual = validator.validate(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with valid email containing numbers EXPECT valid result`() {
        val expected = valid("user123@test123.com")
        val email = "user123@test123.com"

        val actual = validator.validate(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with valid email containing underscore EXPECT valid result`() {
        val expected = valid("user_name@example.com")
        val email = "user_name@example.com"

        val actual = validator.validate(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with empty email and not required EXPECT invalid with empty error`() {
        val expected = invalid(EmailValidationError.EMPTY)
        val email = ""

        val actual = validator.validate(email, required = false)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with empty email and required EXPECT valid result`() {
        val expected = valid("")
        val email = ""

        val actual = validator.validate(email, required = true)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with blank email EXPECT invalid with empty error`() {
        val expected = invalid(EmailValidationError.EMPTY)
        val email = "   "

        val actual = validator.validate(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email missing @ symbol EXPECT invalid with pattern error`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = "testexample.com"

        val actual = validator.validate(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email missing domain EXPECT invalid with pattern error`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = "test@"

        val actual = validator.validate(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email missing local part EXPECT invalid with pattern error`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = "@example.com"

        val actual = validator.validate(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email containing spaces EXPECT invalid with pattern error`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = "test user@example.com"

        val actual = validator.validate(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email containing special characters EXPECT invalid with pattern error`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = "test!user@example.com"

        val actual = validator.validate(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email missing top level domain EXPECT invalid with pattern error`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = "test@example"

        val actual = validator.validate(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email containing multiple @ symbols EXPECT invalid with pattern error`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = "test@@example.com"

        val actual = validator.validate(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email containing subdomain EXPECT invalid with pattern error`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = "test@mail.example.com"

        val actual = validator.validate(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email containing hyphen in domain EXPECT valid result`() {
        val expected = valid("test@my-domain.com")
        val email = "test@my-domain.com"

        val actual = validator.validate(email)

        assertEquals(expected, actual)
    }
}
