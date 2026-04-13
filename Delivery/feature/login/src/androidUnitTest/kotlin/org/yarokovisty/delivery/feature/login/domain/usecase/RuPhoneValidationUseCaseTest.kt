package org.yarokovisty.delivery.feature.login.domain.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.yarokovisty.delivery.common.validation.error.PhoneValidationError
import org.yarokovisty.delivery.common.validation.validator.PhoneValidator
import org.yarokovisty.delivery.util.validation.validated.Validated
import org.yarokovisty.delivery.util.validation.validated.invalid
import org.yarokovisty.delivery.util.validation.validated.valid
import kotlin.test.Test
import kotlin.test.assertEquals

class RuPhoneValidationUseCaseTest {

    private val phoneValidator: PhoneValidator = mockk()
    private val useCase = RuPhoneValidationUseCase(phoneValidator)

    private companion object {
        const val RU_PHONE_LENGTH = 11
        const val VALID_PHONE = "79123456789"
        const val SHORT_PHONE = "7912345678"
        const val LONG_PHONE = "791234567890"
    }

    @Test
    fun `invoke with valid 11-digit phone EXPECT valid result`() {
        val expected: Validated<PhoneValidationError, String> = valid(VALID_PHONE)
        every { phoneValidator.validate(VALID_PHONE, RU_PHONE_LENGTH) } returns expected

        val actual = useCase(VALID_PHONE)

        assertEquals(expected, actual)
    }

    @Test
    fun `invoke with invalid length phone EXPECT invalid with length error`() {
        val expected: Validated<PhoneValidationError, String> = invalid(PhoneValidationError.INVALID_LENGTH)
        every { phoneValidator.validate(SHORT_PHONE, RU_PHONE_LENGTH) } returns expected

        val actual = useCase(SHORT_PHONE)

        assertEquals(expected, actual)
    }

    @Test
    fun `invoke EXPECT phone validator called with phone and length 11`() {
        every { phoneValidator.validate(any(), any()) } returns valid(VALID_PHONE)

        useCase(VALID_PHONE)

        verify { phoneValidator.validate(VALID_PHONE, RU_PHONE_LENGTH) }
    }

    @Test
    fun `invoke with empty phone EXPECT invalid result`() {
        val expected: Validated<PhoneValidationError, String> = invalid(PhoneValidationError.EMPTY)
        every { phoneValidator.validate("", RU_PHONE_LENGTH) } returns expected

        val actual = useCase("")

        assertEquals(expected, actual)
    }

    @Test
    fun `invoke with long phone EXPECT invalid result`() {
        val expected: Validated<PhoneValidationError, String> = invalid(PhoneValidationError.INVALID_LENGTH)
        every { phoneValidator.validate(LONG_PHONE, RU_PHONE_LENGTH) } returns expected

        val actual = useCase(LONG_PHONE)

        assertEquals(expected, actual)
    }
}
