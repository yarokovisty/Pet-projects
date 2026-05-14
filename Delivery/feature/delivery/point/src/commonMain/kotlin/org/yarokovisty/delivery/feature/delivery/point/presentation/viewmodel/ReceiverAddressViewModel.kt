package org.yarokovisty.delivery.feature.delivery.point.presentation.viewmodel

import org.yarokovisty.delivery.common.delivery.point.domain.repository.AddressRepository
import org.yarokovisty.delivery.common.validation.error.AddressValidationError
import org.yarokovisty.delivery.common.validation.validator.AddressValidator
import org.yarokovisty.delivery.core.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.delivery.point.navigation.ReceiverAddressRouter
import org.yarokovisty.delivery.feature.delivery.point.presentation.intent.ReceiverAddressIntent
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.ReceiverAddressState
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.apartmentInvalid
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.apartmentValid
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.getReceiverAddress
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.houseInvalid
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.houseValid
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.initialReceiverAddressState
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.streetInvalid
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.streetValid
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.updateApartment
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.updateComment
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.updateHouse
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.updateNonContactedCheckBox
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.updateNonContactedTipShowing
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.updateStreet
import org.yarokovisty.delivery.util.validation.validated.fold

internal class ReceiverAddressViewModel(
    private val addressRepository: AddressRepository,
    private val addressValidator: AddressValidator,
    private val router: ReceiverAddressRouter,
    maxSteps: Int,
) : BaseViewModel<ReceiverAddressState, ReceiverAddressIntent, Nothing>(
    initialReceiverAddressState(currentStep = CURRENT_STEP, maxSteps = maxSteps)
) {
    private companion object {

        const val CURRENT_STEP = 5
    }

    override fun onIntent(intent: ReceiverAddressIntent) {
        when (intent) {
            is ReceiverAddressIntent.Back -> back()
            is ReceiverAddressIntent.ClickContinue -> nextStep()
            is ReceiverAddressIntent.ClickNonContactedCheckbox -> changeNonContactedCheckbox()
            is ReceiverAddressIntent.ClickNonContactedTip -> changeNonContactedTipShowing()
            is ReceiverAddressIntent.InputApartment -> changeApartment(intent.apartment)
            is ReceiverAddressIntent.InputComment -> changeComment(intent.comment)
            is ReceiverAddressIntent.InputHouse -> changeHouse(intent.house)
            is ReceiverAddressIntent.InputStreet -> changeStreet(intent.street)
        }
    }

    private fun back() {
        router.back()
    }

    private fun nextStep() {
        if (!validateInputs()) return

        saveAddress()
    }

    private fun validateInputs(): Boolean {
        val contentState = stateValue.contentState
        val streetValidation = validateAddress(
            input = contentState.street.value,
            onValidUpdate = { streetValid() },
            onInvalidUpdate = { streetInvalid(it) }
        )
        val houseValidation = validateAddress(
            input = contentState.house.value,
            onValidUpdate = { houseValid() },
            onInvalidUpdate = { houseInvalid(it) }
        )
        val apartmentValidation = validateAddress(
            input = contentState.apartment.value,
            onValidUpdate = { apartmentValid() },
            onInvalidUpdate = { apartmentInvalid(it) }
        )

        return streetValidation && houseValidation && apartmentValidation
    }

    private fun validateAddress(
        input: String,
        onValidUpdate: ReceiverAddressState.() -> ReceiverAddressState,
        onInvalidUpdate: ReceiverAddressState.(AddressValidationError) -> ReceiverAddressState
    ): Boolean =
        addressValidator.validate(input).fold(
            onValid = {
                updateState { onValidUpdate() }
                true
            },
            onInvalid = { reason ->
                updateState { onInvalidUpdate(reason) }
                false
            }
        )

    private fun saveAddress() {
        launch {
            val receiverAddress = stateValue.getReceiverAddress()
            addressRepository.setReceiver(receiverAddress)
            router.openPayerScreen()
        }
    }

    private fun changeNonContactedCheckbox() {
        updateState { updateNonContactedCheckBox() }
    }

    private fun changeNonContactedTipShowing() {
        updateState { updateNonContactedTipShowing() }
    }

    private fun changeStreet(street: String) {
        updateState { updateStreet(street) }
    }

    private fun changeHouse(house: String) {
        updateState { updateHouse(house) }
    }

    private fun changeApartment(apartment: String) {
        updateState { updateApartment(apartment) }
    }

    private fun changeComment(comment: String) {
        updateState { updateComment(comment) }
    }
}
