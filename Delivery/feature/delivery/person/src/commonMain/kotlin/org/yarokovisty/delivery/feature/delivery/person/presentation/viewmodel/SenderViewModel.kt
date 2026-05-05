package org.yarokovisty.delivery.feature.delivery.person.presentation.viewmodel

import org.yarokovisty.delivery.common.delivery.person.domain.repository.PersonRepository
import org.yarokovisty.delivery.common.profile.main.domain.usecase.GetUserUseCase
import org.yarokovisty.delivery.common.validation.usecase.RuPhoneValidateUseCase
import org.yarokovisty.delivery.common.validation.validator.NameValidator
import org.yarokovisty.delivery.core.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.delivery.person.navigation.SenderRouter
import org.yarokovisty.delivery.feature.delivery.person.presentation.converter.toPersonInfo
import org.yarokovisty.delivery.feature.delivery.person.presentation.intent.PersonIntent
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.PersonState
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.firstnameInvalid
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.firstnameValid
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.getUpdatedPersonInfo
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.initial
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.lastnameInvalid
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.lastnameValid
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.phoneNumberInvalid
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.phoneNumberValid
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.setPersonInfo
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.updateFirstname
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.updateLastname
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.updateMiddlename
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.updatePhoneNumber
import org.yarokovisty.delivery.util.phone.PhoneNumberFormatter
import org.yarokovisty.delivery.util.phone.clearPhoneNumber
import org.yarokovisty.delivery.util.validation.validated.fold

internal class SenderViewModel(
    private val personRepository: PersonRepository,
    private val getUserUseCase: GetUserUseCase,
    private val ruPhoneValidateUseCase: RuPhoneValidateUseCase,
    private val nameValidator: NameValidator,
    private val phoneNumberFormatter: PhoneNumberFormatter,
    private val router: SenderRouter,
    maxSteps: Int
) : BaseViewModel<PersonState, PersonIntent, Nothing>(
    initial(CURRENT_STEP, maxSteps)
) {
    private companion object {

        const val CURRENT_STEP = 3
    }

    init {
        loadUser()
    }

    private fun loadUser() {
        launch {
            getUserUseCase()?.toPersonInfo()?.let { receiverInfo ->
                val phoneNumberFormatted = phoneNumberFormatter.format(receiverInfo.phone)
                updateState { setPersonInfo(receiverInfo, phoneNumberFormatted) }
            }
        }
    }

    override fun onIntent(intent: PersonIntent) {
        when (intent) {
            is PersonIntent.Back -> back()
            is PersonIntent.ClickContinue -> nextStep()
            is PersonIntent.InputFirstname -> changeFirstname(intent.firstname)
            is PersonIntent.InputLastname -> changeLastname(intent.lastname)
            is PersonIntent.InputMiddlename -> changeMiddlename(intent.middlename)
            is PersonIntent.InputPhoneNumber -> changePhoneNumber(intent.phoneNumber)
        }
    }

    private fun back() {
        router.back()
    }

    private fun nextStep() {
        if (!validateInputData()) return

        launch {
            val senderInfo = stateValue.getUpdatedPersonInfo()
            personRepository.setSender(senderInfo)

            router.openSenderAddressScreen()
        }
    }

    private fun validateInputData(): Boolean {
        val firstnameValid = validateFirstname()
        val lastnameValid = validateLastname()
        val phoneNumberValid = validatePhoneNumber()

        return firstnameValid && lastnameValid && phoneNumberValid
    }

    private fun validateFirstname(): Boolean {
        val firstname = stateValue.contentState.firstname.value
        return nameValidator.validate(firstname).fold(
            onValid = {
                updateState { firstnameValid() }
                true
            },
            onInvalid = {
                updateState { firstnameInvalid(it) }
                false
            }
        )
    }

    private fun validateLastname(): Boolean {
        val lastname = stateValue.contentState.lastname.value
        return nameValidator.validate(lastname).fold(
            onValid = {
                updateState { lastnameValid() }
                true
            },
            onInvalid = { error ->
                updateState { lastnameInvalid(error) }
                false
            }
        )
    }

    private fun validatePhoneNumber(): Boolean {
        val clearPhoneNumber = stateValue.contentState.phoneNumber.value.clearPhoneNumber()
        return ruPhoneValidateUseCase(clearPhoneNumber).fold(
            onValid = {
                updateState { phoneNumberValid() }
                true
            },
            onInvalid = { error ->
                updateState { phoneNumberInvalid(error) }
                false
            }
        )
    }

    private fun changeFirstname(firstname: String) {
        updateState { updateFirstname(firstname) }
    }

    private fun changeLastname(lastname: String) {
        updateState { updateLastname(lastname) }
    }

    private fun changeMiddlename(middlename: String) {
        updateState { updateMiddlename(middlename) }
    }

    private fun changePhoneNumber(phoneNumber: String) {
        updateState { updatePhoneNumber(phoneNumber) }
    }
}
