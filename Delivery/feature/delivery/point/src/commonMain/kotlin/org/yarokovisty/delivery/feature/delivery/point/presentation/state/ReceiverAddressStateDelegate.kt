package org.yarokovisty.delivery.feature.delivery.point.presentation.state

import org.yarokovisty.delivery.common.delivery.point.domain.entity.Address
import org.yarokovisty.delivery.common.delivery.presentation.StepState
import org.yarokovisty.delivery.common.validation.error.AddressValidationError

internal fun initialReceiverAddressState(currentStep: Int, maxSteps: Int): ReceiverAddressState =
    ReceiverAddressState(
        stepState = StepState(
            progress = currentStep,
            maxProgress = maxSteps
        ),
        contentState = ReceiverAddressContentState(
            street = InputState.INITIAL,
            house = InputState.INITIAL,
            apartment = InputState.INITIAL,
            comment = "",
            nonContactedState = NonContactedState(
                checked = false,
                tipShowing = false
            )
        )
    )

internal fun ReceiverAddressState.updateStreet(street: String): ReceiverAddressState =
    copy(
        contentState = contentState.copy(
            street = contentState.street.copy(
                value = street,
                fieldStatus = FieldStatus.NotValidated
            )
        )
    )

internal fun ReceiverAddressState.streetValid(): ReceiverAddressState =
    copy(
        contentState = contentState.copy(
            street = contentState.street.copy(fieldStatus = FieldStatus.Valid)
        )
    )

internal fun ReceiverAddressState.streetInvalid(error: AddressValidationError): ReceiverAddressState =
    copy(
        contentState = contentState.copy(
            street = contentState.street.copy(fieldStatus = FieldStatus.Invalid(error))
        )
    )

internal fun ReceiverAddressState.updateHouse(house: String): ReceiverAddressState =
    copy(
        contentState = contentState.copy(
            house = contentState.house.copy(
                value = house,
                fieldStatus = FieldStatus.NotValidated
            )
        )
    )

internal fun ReceiverAddressState.houseValid(): ReceiverAddressState =
    copy(
        contentState = contentState.copy(
            house = contentState.house.copy(fieldStatus = FieldStatus.Valid)
        )
    )

internal fun ReceiverAddressState.houseInvalid(error: AddressValidationError): ReceiverAddressState =
    copy(
        contentState = contentState.copy(
            house = contentState.house.copy(fieldStatus = FieldStatus.Invalid(error))
        )
    )

internal fun ReceiverAddressState.updateApartment(apartment: String): ReceiverAddressState =
    copy(
        contentState = contentState.copy(
            apartment = contentState.apartment.copy(
                value = apartment,
                fieldStatus = FieldStatus.NotValidated
            )
        )
    )

internal fun ReceiverAddressState.apartmentValid(): ReceiverAddressState =
    copy(
        contentState = contentState.copy(
            apartment = contentState.apartment.copy(fieldStatus = FieldStatus.Valid)
        )
    )

internal fun ReceiverAddressState.apartmentInvalid(error: AddressValidationError): ReceiverAddressState =
    copy(
        contentState = contentState.copy(
            apartment = contentState.apartment.copy(fieldStatus = FieldStatus.Invalid(error))
        )
    )

internal fun ReceiverAddressState.updateComment(comment: String): ReceiverAddressState =
    copy(
        contentState = contentState.copy(comment = comment)
    )

internal fun ReceiverAddressState.getReceiverAddress(): Address =
    Address(
        street = contentState.street.value,
        house = contentState.house.value,
        apartment = contentState.apartment.value,
        comment = contentState.comment,
        nonContacted = contentState.nonContactedState.checked
    )

internal fun ReceiverAddressState.updateNonContactedCheckBox(): ReceiverAddressState {
    val currentChecked = contentState.nonContactedState.checked

    return copy(
        contentState = contentState.copy(
            nonContactedState = contentState.nonContactedState.copy(checked = !currentChecked)
        )
    )
}

internal fun ReceiverAddressState.updateNonContactedTipShowing(): ReceiverAddressState {
    val currentShowing = contentState.nonContactedState.tipShowing

    return copy(
        contentState = contentState.copy(
            nonContactedState = contentState.nonContactedState.copy(tipShowing = !currentShowing)
        )
    )
}
