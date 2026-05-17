package org.yarokovisty.delivery.feature.delivery.point.presentation.viewmodel

import org.yarokovisty.delivery.common.delivery.point.domain.repository.AddressRepository
import org.yarokovisty.delivery.common.validation.error.AddressValidationError
import org.yarokovisty.delivery.common.validation.validator.AddressValidator
import org.yarokovisty.delivery.core.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.delivery.point.navigation.AddressScreenType
import org.yarokovisty.delivery.feature.delivery.point.navigation.SenderAddressRouter
import org.yarokovisty.delivery.feature.delivery.point.presentation.intent.SenderAddressIntent
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.SenderAddressState
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.apartmentInvalid
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.apartmentValid
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.getSenderAddress
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.houseInvalid
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.houseValid
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.initialSenderAddressState
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.setAddress
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.streetInvalid
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.streetValid
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.updateApartment
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.updateComment
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.updateHouse
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.updateStreet
import org.yarokovisty.delivery.util.validation.validated.fold

internal class SenderAddressViewModel(
    private val addressRepository: AddressRepository,
    private val addressValidator: AddressValidator,
    private val router: SenderAddressRouter,
    private val screenType: AddressScreenType,
    maxSteps: Int,
) : BaseViewModel<SenderAddressState, SenderAddressIntent, Nothing>(
    initialSenderAddressState(currentStep = CURRENT_STEP, maxSteps = maxSteps)
) {

    private companion object {

        const val CURRENT_STEP = 4
    }

    init {
        if (screenType == AddressScreenType.EDIT) {
            loadAddress()
        }
    }

    private fun loadAddress() {
        launch {
            addressRepository.getSender()?.let { address ->
                updateState { setAddress(address) }
            }
        }
    }

    override fun onIntent(intent: SenderAddressIntent) {
        when (intent) {
            is SenderAddressIntent.Back -> back()
            is SenderAddressIntent.ClickContinue -> nextStep()
            is SenderAddressIntent.InputApartment -> changeApartment(intent.apartment)
            is SenderAddressIntent.InputComment -> changeComment(intent.comment)
            is SenderAddressIntent.InputHouse -> changeHouse(intent.house)
            is SenderAddressIntent.InputStreet -> changeStreet(intent.street)
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
        onValidUpdate: SenderAddressState.() -> SenderAddressState,
        onInvalidUpdate: SenderAddressState.(AddressValidationError) -> SenderAddressState
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
            val senderAddress = stateValue.getSenderAddress()
            addressRepository.setSender(senderAddress)

            openNextScreen()
        }
    }

    private fun openNextScreen() {
        if (screenType == AddressScreenType.NEW) {
            router.openReceiverAddress()
        } else {
            router.back()
        }
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
