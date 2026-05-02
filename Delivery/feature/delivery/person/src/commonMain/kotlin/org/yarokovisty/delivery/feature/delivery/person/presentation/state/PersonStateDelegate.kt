package org.yarokovisty.delivery.feature.delivery.person.presentation.state

import org.yarokovisty.delivery.common.delivery.person.domain.entity.PersonInfo
import org.yarokovisty.delivery.common.validation.error.NameValidationError
import org.yarokovisty.delivery.common.validation.error.PhoneValidationError
import org.yarokovisty.delivery.util.phone.clearPhoneNumber

internal fun initial(currentStep: Int, maxSteps: Int): PersonState =
    PersonState(
        stepState = StepState(
            progress = currentStep,
            maxProgress = maxSteps,
        ),
        contentState = ContentState(
            person = null,
            firstname = NameState(
                value = "",
                fieldStatus = NameFieldStatus.NotValidated,
            ),
            lastname = NameState(
                value = "",
                fieldStatus = NameFieldStatus.NotValidated,
            ),
            middlename = "",
            phoneNumber = PhoneNumberState(
                value = "",
                fieldStatus = PhoneFieldStatus.NotValidated,
            )
        )
    )

internal fun PersonState.setPersonInfo(personInfo: PersonInfo, phoneNumber: String): PersonState =
    copy(
        contentState = contentState.copy(
            person = personInfo,
            firstname = contentState.firstname.copy(
                value = personInfo.firstname,
                fieldStatus = NameFieldStatus.NotValidated
            ),
            lastname = contentState.lastname.copy(
                value = personInfo.lastname,
                fieldStatus = NameFieldStatus.NotValidated,
            ),
            middlename = personInfo.middlename ?: "",
            phoneNumber = contentState.phoneNumber.copy(
                value = phoneNumber,
                fieldStatus = PhoneFieldStatus.NotValidated
            )
        )
    )

internal fun PersonState.updateFirstname(firstname: String): PersonState =
    copy(
        contentState = contentState.copy(
            firstname = contentState.firstname.copy(
                value = firstname,
                fieldStatus = NameFieldStatus.NotValidated,
            )
        )
    )

internal fun PersonState.firstnameValid(): PersonState =
    copy(
        contentState = contentState.copy(
            firstname = contentState.firstname.copy(fieldStatus = NameFieldStatus.Valid)
        )
    )

internal fun PersonState.firstnameInvalid(error: NameValidationError): PersonState =
    copy(
        contentState = contentState.copy(
            firstname = contentState.firstname.copy(fieldStatus = NameFieldStatus.Invalid(error))
        )
    )

internal fun PersonState.updateLastname(lastname: String): PersonState =
    copy(
        contentState = contentState.copy(
            lastname = contentState.lastname.copy(
                value = lastname,
                fieldStatus = NameFieldStatus.NotValidated,
            )
        )
    )

internal fun PersonState.lastnameValid(): PersonState =
    copy(
        contentState = contentState.copy(
            lastname = contentState.lastname.copy(fieldStatus = NameFieldStatus.Valid)
        )
    )

internal fun PersonState.lastnameInvalid(error: NameValidationError): PersonState =
    copy(
        contentState = contentState.copy(
            lastname = contentState.lastname.copy(fieldStatus = NameFieldStatus.Invalid(error))
        )
    )

internal fun PersonState.updateMiddlename(middlename: String): PersonState =
    copy(
        contentState = contentState.copy(middlename = middlename)
    )

internal fun PersonState.updatePhoneNumber(phoneNumber: String): PersonState =
    copy(
        contentState = contentState.copy(
            phoneNumber = contentState.phoneNumber.copy(
                value = phoneNumber,
                fieldStatus = PhoneFieldStatus.NotValidated,
            )
        )
    )

internal fun PersonState.phoneNumberValid(): PersonState =
    copy(
        contentState = contentState.copy(
            phoneNumber = contentState.phoneNumber.copy(fieldStatus = PhoneFieldStatus.Valid)
        )
    )

internal fun PersonState.phoneNumberInvalid(error: PhoneValidationError): PersonState =
    copy(
        contentState = contentState.copy(
            phoneNumber = contentState.phoneNumber.copy(fieldStatus = PhoneFieldStatus.Invalid(error))
        )
    )

internal fun PersonState.getUpdatedPersonInfo(): PersonInfo =
    PersonInfo(
        firstname = contentState.firstname.value,
        lastname = contentState.lastname.value,
        middlename = contentState.middlename.ifEmpty { null },
        phone = contentState.phoneNumber.value.clearPhoneNumber()
    )
