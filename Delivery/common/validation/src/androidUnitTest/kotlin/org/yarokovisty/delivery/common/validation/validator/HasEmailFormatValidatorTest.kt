package org.yarokovisty.delivery.common.validation.validator

import org.yarokovisty.delivery.common.validation.error.EmailValidationError
import org.yarokovisty.delivery.util.validation.validated.invalid
import org.yarokovisty.delivery.util.validation.validated.valid
import kotlin.test.Test
import kotlin.test.assertEquals

class HasEmailFormatValidatorTest {

    private val validator = hasEmailFormatValidator()

    @Test
    fun `validate with valid email format EXPECT valid result`() {
        val expected = valid("user@example.com")
        val email = "user@example.com"

        val actual = validator.invoke(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email containing numbers EXPECT valid result`() {
        val expected = valid("user123@test456.org")
        val email = "user123@test456.org"

        val actual = validator.invoke(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email containing underscore EXPECT valid result`() {
        val expected = valid("first_last@company.net")
        val email = "first_last@company.net"

        val actual = validator.invoke(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email without @ symbol EXPECT invalid result`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = "userexample.com"

        val actual = validator.invoke(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email without domain EXPECT invalid result`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = "user@"

        val actual = validator.invoke(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email without local part EXPECT invalid result`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = "@example.com"

        val actual = validator.invoke(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email without top-level domain EXPECT invalid result`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = "user@example"

        val actual = validator.invoke(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email containing spaces EXPECT invalid result`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = "user name@example.com"

        val actual = validator.invoke(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email containing special characters EXPECT invalid result`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = "user!name@example.com"

        val actual = validator.invoke(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email containing subdomain EXPECT invalid result`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = "user@mail.example.com"

        val actual = validator.invoke(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email containing multiple subdomains EXPECT invalid result`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = "user@a.b.c.example.com"

        val actual = validator.invoke(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email containing hyphen in domain EXPECT valid result`() {
        val expected = valid("user@my-domain.com")
        val email = "user@my-domain.com"

        val actual = validator.invoke(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email containing multiple @ symbols EXPECT invalid result`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = "user@@example.com"

        val actual = validator.invoke(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email starting with number EXPECT valid result`() {
        val expected = valid("123user@example.com")
        val email = "123user@example.com"

        val actual = validator.invoke(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with email containing dot in local part EXPECT invalid result`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = "first.last@example.com"

        val actual = validator.invoke(email)

        assertEquals(expected, actual)
    }

    @Test
    fun `validate with empty email EXPECT invalid result`() {
        val expected = invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        val email = ""

        val actual = validator.invoke(email)

        assertEquals(expected, actual)
    }
}
