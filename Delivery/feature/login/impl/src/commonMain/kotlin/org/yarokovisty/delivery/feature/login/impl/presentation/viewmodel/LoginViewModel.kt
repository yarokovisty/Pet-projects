package org.yarokovisty.delivery.feature.login.impl.presentation.viewmodel

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import org.yarokovisty.delivery.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.login.api.domain.repository.LoginRepository
import org.yarokovisty.delivery.feature.login.api.error.LoginError
import org.yarokovisty.delivery.feature.login.impl.domain.usecase.RuPhoneValidationUseCase
import org.yarokovisty.delivery.feature.login.impl.domain.usecase.SigninUseCase
import org.yarokovisty.delivery.feature.login.impl.domain.usecase.StartCountDownUseCase
import org.yarokovisty.delivery.feature.login.impl.domain.validator.OtpCodeFormatValidator
import org.yarokovisty.delivery.feature.login.impl.presentation.event.LoginEvent
import org.yarokovisty.delivery.feature.login.impl.presentation.intent.LoginIntent
import org.yarokovisty.delivery.feature.login.impl.presentation.router.LoginRouter
import org.yarokovisty.delivery.feature.login.impl.presentation.state.LoginState
import org.yarokovisty.delivery.feature.login.impl.presentation.state.changeOtpCode
import org.yarokovisty.delivery.feature.login.impl.presentation.state.changePhoneNumber
import org.yarokovisty.delivery.feature.login.impl.presentation.state.clearOtpRetryTimer
import org.yarokovisty.delivery.feature.login.impl.presentation.state.initial
import org.yarokovisty.delivery.feature.login.impl.presentation.state.invalidFormatOtpCode
import org.yarokovisty.delivery.feature.login.impl.presentation.state.invalidOtpCode
import org.yarokovisty.delivery.feature.login.impl.presentation.state.invalidPhoneNumber
import org.yarokovisty.delivery.feature.login.impl.presentation.state.showOtpInput
import org.yarokovisty.delivery.feature.login.impl.presentation.state.tickOtpRetryTimer
import org.yarokovisty.delivery.feature.login.impl.presentation.state.validOtpCode
import org.yarokovisty.delivery.feature.login.impl.presentation.state.validPhoneNumber
import org.yarokovisty.delivery.feature.login.impl.util.toSecondRounded
import org.yarokovisty.delivery.util.phone.clearPhoneNumber
import org.yarokovisty.delivery.util.validation.validated.fold

internal class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val ruPhoneValidationUseCase: RuPhoneValidationUseCase,
    private val signinUseCase: SigninUseCase,
    private val startCountDownUseCase: StartCountDownUseCase,
    private val otpCodeFormatValidator: OtpCodeFormatValidator,
    private val router: LoginRouter
) : BaseViewModel<LoginState, LoginIntent, LoginEvent>(initial()) {

    private var otpRetryTimerJob: Job? = null

    override fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.Back -> back()
            is LoginIntent.ClickLogin -> nextStep()
            is LoginIntent.InputOtpCode -> changeOtpCode(intent.code)
            is LoginIntent.InputPhoneNumber -> changePhoneNumber(intent.phone)
            is LoginIntent.RetrySendOtp -> requestOtpCode()
        }
    }

    private fun back() {
        router.back()
    }

    private fun nextStep() {
        val currentState = stateValue

        if (currentState.otpCodeState == null) {
            requestOtpCode()
        } else {
            signin()
        }
    }

    private fun requestOtpCode() {
        val clearPhoneNumber = stateValue.phoneNumberState.phoneNumber.clearPhoneNumber()

        if (!validatePhoneNumber(clearPhoneNumber)) return

        launchTrying {
            val expireTime = loginRepository.requestOtp(clearPhoneNumber)

            updateState { showOtpInput() }

            startOtpRetryTimer(expireTime)
        } handle { handleRequestOtpCodeError() }
    }

    private fun validatePhoneNumber(phoneNumber: String): Boolean =
        ruPhoneValidationUseCase(phoneNumber)
            .fold(
                onValid = {
                    updateState { validPhoneNumber() }
                    true
                },
                onInvalid = { reason ->
                    updateState { invalidPhoneNumber(reason) }
                    false
                }
            )

    private fun startOtpRetryTimer(expireTime: Long) {
        otpRetryTimerJob?.cancel()
        otpRetryTimerJob = startCountDownUseCase(expireTime)
            .onEach { time ->
                val secondsLeft = time.toSecondRounded()
                updateState { tickOtpRetryTimer(secondsLeft) }
            }
            .onCompletion {
                updateState { clearOtpRetryTimer() }
            }
            .launchIn(scope)
    }

    private fun handleRequestOtpCodeError() {
        launch {
            emitEvent(LoginEvent.OtpRequestError)
        }
    }

    private fun signin() {
        val otpCode = stateValue.otpCodeState?.code ?: return
        val clearPhoneNumber = stateValue.phoneNumberState.phoneNumber.clearPhoneNumber()

        if (!validateOtpCode(otpCode)) return

        launchTrying {
            signinUseCase(clearPhoneNumber, otpCode.toInt())
            router.openProfileScreen()
        } handle (::handleSigninError)
    }

    private fun validateOtpCode(code: String): Boolean =
        otpCodeFormatValidator.validate(code)
            .fold(
                onValid = {
                    updateState { validOtpCode() }
                    true
                },
                onInvalid = {
                    updateState { invalidFormatOtpCode(it) }
                    false
                }
            )

    private fun handleSigninError(error: Throwable) {
        if (error is LoginError.InvalidOtp) {
            updateState { invalidOtpCode() }
        } else {
            launch { emitEvent(LoginEvent.SigninError) }
        }
    }

    private fun changeOtpCode(code: String) {
        updateState { this.changeOtpCode(code) }
    }

    private fun changePhoneNumber(phoneNumber: String) {
        otpRetryTimerJob?.cancel()
        updateState { this.changePhoneNumber(phoneNumber) }
    }
}
