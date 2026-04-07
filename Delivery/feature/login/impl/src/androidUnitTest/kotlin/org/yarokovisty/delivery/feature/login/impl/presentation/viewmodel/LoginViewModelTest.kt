package org.yarokovisty.delivery.feature.login.impl.presentation.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.yarokovisty.delivery.common.validation.error.PhoneValidationError
import org.yarokovisty.delivery.feature.login.api.domain.repository.LoginRepository
import org.yarokovisty.delivery.feature.login.api.error.LoginError
import org.yarokovisty.delivery.feature.login.impl.domain.usecase.RuPhoneValidationUseCase
import org.yarokovisty.delivery.feature.login.impl.domain.usecase.SigninUseCase
import org.yarokovisty.delivery.feature.login.impl.domain.usecase.StartCountDownUseCase
import org.yarokovisty.delivery.feature.login.impl.domain.validator.OtpCodeFormatValidationError
import org.yarokovisty.delivery.feature.login.impl.domain.validator.OtpCodeFormatValidator
import org.yarokovisty.delivery.feature.login.impl.presentation.intent.LoginIntent
import org.yarokovisty.delivery.feature.login.impl.presentation.router.LoginRouter
import org.yarokovisty.delivery.feature.login.impl.presentation.state.OtpFieldStatus
import org.yarokovisty.delivery.feature.login.impl.presentation.state.PhoneFieldStatus
import org.yarokovisty.delivery.util.unitTest.MainDispatcherRule
import org.yarokovisty.delivery.util.validation.validated.invalid
import org.yarokovisty.delivery.util.validation.validated.valid
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val loginRepository: LoginRepository = mockk(relaxed = true)
    private val ruPhoneValidationUseCase: RuPhoneValidationUseCase = mockk()
    private val signinUseCase: SigninUseCase = mockk(relaxed = true)
    private val startCountDownUseCase: StartCountDownUseCase = mockk()
    private val otpCodeFormatValidator: OtpCodeFormatValidator = mockk()
    private val router: LoginRouter = mockk(relaxed = true)

    private companion object {
        const val TEST_PHONE = "79123456789"
        const val TEST_OTP_CODE = "123456"
        const val TEST_EXPIRE_TIME = 60000L
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `input phone number EXPECT state updated with new phone`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(LoginIntent.InputPhoneNumber(TEST_PHONE))
        advanceUntilIdle()

        assertEquals(TEST_PHONE, viewModel.state.value.phoneNumberState.phoneNumber)
    }

    @Test
    fun `input otp code EXPECT state updated with new code`() = runTest {
        every { ruPhoneValidationUseCase(any()) } returns valid(TEST_PHONE)
        coEvery { loginRepository.requestOtp(any()) } returns TEST_EXPIRE_TIME
        every { startCountDownUseCase(any()) } returns flowOf()
        val viewModel = createViewModel()

        viewModel.onIntent(LoginIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(LoginIntent.ClickLogin)
        advanceUntilIdle()
        viewModel.onIntent(LoginIntent.InputOtpCode(TEST_OTP_CODE))
        advanceUntilIdle()

        assertEquals(TEST_OTP_CODE, viewModel.state.value.otpCodeState?.code)
    }

    @Test
    fun `click login when otp code state is null EXPECT request otp`() = runTest {
        every { ruPhoneValidationUseCase(any()) } returns valid(TEST_PHONE)
        coEvery { loginRepository.requestOtp(any()) } returns TEST_EXPIRE_TIME
        every { startCountDownUseCase(any()) } returns flowOf()
        val viewModel = createViewModel()

        viewModel.onIntent(LoginIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(LoginIntent.ClickLogin)
        advanceUntilIdle()

        coVerify { loginRepository.requestOtp(TEST_PHONE) }
    }

    @Test
    fun `request otp with valid phone EXPECT state shows otp input`() = runTest {
        every { ruPhoneValidationUseCase(any()) } returns valid(TEST_PHONE)
        coEvery { loginRepository.requestOtp(any()) } returns TEST_EXPIRE_TIME
        every { startCountDownUseCase(any()) } returns flowOf()
        val viewModel = createViewModel()

        viewModel.onIntent(LoginIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(LoginIntent.ClickLogin)
        advanceUntilIdle()

        assertNotNull(viewModel.state.value.otpCodeState)
    }

    @Test
    fun `request otp with invalid phone EXPECT state shows phone validation error`() = runTest {
        every { ruPhoneValidationUseCase(any()) } returns invalid(PhoneValidationError.INVALID_LENGTH)
        val viewModel = createViewModel()

        viewModel.onIntent(LoginIntent.InputPhoneNumber("123"))
        viewModel.onIntent(LoginIntent.ClickLogin)
        advanceUntilIdle()

        val fieldStatus = viewModel.state.value.phoneNumberState.fieldStatus
        assertTrue(fieldStatus is PhoneFieldStatus.Invalid)
        assertEquals(PhoneValidationError.INVALID_LENGTH, (fieldStatus as PhoneFieldStatus.Invalid).error)
    }

    @Test
    fun `request otp with valid phone EXPECT phone field status valid`() = runTest {
        every { ruPhoneValidationUseCase(any()) } returns valid(TEST_PHONE)
        coEvery { loginRepository.requestOtp(any()) } returns TEST_EXPIRE_TIME
        every { startCountDownUseCase(any()) } returns flowOf()
        val viewModel = createViewModel()

        viewModel.onIntent(LoginIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(LoginIntent.ClickLogin)
        advanceUntilIdle()

        assertTrue(viewModel.state.value.phoneNumberState.fieldStatus is PhoneFieldStatus.Valid)
    }

    @Test
    fun `request otp success EXPECT timer started`() = runTest {
        every { ruPhoneValidationUseCase(any()) } returns valid(TEST_PHONE)
        coEvery { loginRepository.requestOtp(any()) } returns TEST_EXPIRE_TIME
        every { startCountDownUseCase(any()) } returns flowOf(TEST_EXPIRE_TIME)
        val viewModel = createViewModel()

        viewModel.onIntent(LoginIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(LoginIntent.ClickLogin)
        advanceUntilIdle()

        verify { startCountDownUseCase(TEST_EXPIRE_TIME) }
    }

    @Test
    fun `signin with valid otp EXPECT profile screen opened`() = runTest {
        every { ruPhoneValidationUseCase(any()) } returns valid(TEST_PHONE)
        coEvery { loginRepository.requestOtp(any()) } returns TEST_EXPIRE_TIME
        every { startCountDownUseCase(any()) } returns flowOf()
        every { otpCodeFormatValidator.validate(any()) } returns valid(TEST_OTP_CODE)
        coEvery { signinUseCase(any(), any()) } returns Unit
        val viewModel = createViewModel()

        viewModel.onIntent(LoginIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(LoginIntent.ClickLogin)
        advanceUntilIdle()
        viewModel.onIntent(LoginIntent.InputOtpCode(TEST_OTP_CODE))
        viewModel.onIntent(LoginIntent.ClickLogin)
        advanceUntilIdle()

        verify { router.openProfileScreen() }
    }

    @Test
    fun `signin with invalid otp format EXPECT format validation error shown`() = runTest {
        every { ruPhoneValidationUseCase(any()) } returns valid(TEST_PHONE)
        coEvery { loginRepository.requestOtp(any()) } returns TEST_EXPIRE_TIME
        every { startCountDownUseCase(any()) } returns flowOf()
        every { otpCodeFormatValidator.validate(any()) } returns invalid(OtpCodeFormatValidationError.INVALID_LENGTH)
        val viewModel = createViewModel()

        viewModel.onIntent(LoginIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(LoginIntent.ClickLogin)
        advanceUntilIdle()
        viewModel.onIntent(LoginIntent.InputOtpCode("123"))
        viewModel.onIntent(LoginIntent.ClickLogin)
        advanceUntilIdle()

        val fieldStatus = viewModel.state.value.otpCodeState?.fieldStatus
        assertTrue(fieldStatus is OtpFieldStatus.InvalidFormat)
        assertEquals(
            OtpCodeFormatValidationError.INVALID_LENGTH,
            (fieldStatus as OtpFieldStatus.InvalidFormat).error
        )
    }

    @Test
    fun `signin with invalid otp from server EXPECT invalid otp code state`() = runTest {
        every { ruPhoneValidationUseCase(any()) } returns valid(TEST_PHONE)
        coEvery { loginRepository.requestOtp(any()) } returns TEST_EXPIRE_TIME
        every { startCountDownUseCase(any()) } returns flowOf()
        every { otpCodeFormatValidator.validate(any()) } returns valid(TEST_OTP_CODE)
        coEvery { signinUseCase(any(), any()) } throws LoginError.InvalidOtp
        val viewModel = createViewModel()

        viewModel.onIntent(LoginIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(LoginIntent.ClickLogin)
        advanceUntilIdle()
        viewModel.onIntent(LoginIntent.InputOtpCode(TEST_OTP_CODE))
        viewModel.onIntent(LoginIntent.ClickLogin)
        advanceUntilIdle()

        assertTrue(viewModel.state.value.otpCodeState?.fieldStatus is OtpFieldStatus.InvalidCode)
    }

    @Test
    fun `back EXPECT router back called`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(LoginIntent.Back)
        advanceUntilIdle()

        verify { router.back() }
    }

    @Test
    fun `retry send otp EXPECT request otp again`() = runTest {
        every { ruPhoneValidationUseCase(any()) } returns valid(TEST_PHONE)
        coEvery { loginRepository.requestOtp(any()) } returns TEST_EXPIRE_TIME
        every { startCountDownUseCase(any()) } returns flowOf()
        val viewModel = createViewModel()

        viewModel.onIntent(LoginIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(LoginIntent.ClickLogin)
        advanceUntilIdle()
        viewModel.onIntent(LoginIntent.RetrySendOtp)
        advanceUntilIdle()

        coVerify(exactly = 2) { loginRepository.requestOtp(TEST_PHONE) }
    }

    private fun createViewModel() =
        LoginViewModel(
            loginRepository,
            ruPhoneValidationUseCase,
            signinUseCase,
            startCountDownUseCase,
            otpCodeFormatValidator,
            router
        )
}
