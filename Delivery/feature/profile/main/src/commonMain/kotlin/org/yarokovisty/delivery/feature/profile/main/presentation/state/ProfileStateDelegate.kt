package org.yarokovisty.delivery.feature.profile.main.presentation.state

import org.yarokovisty.delivery.common.validation.error.EmailValidationError
import org.yarokovisty.delivery.feature.profile.main.domain.entity.User

internal fun initial() =
    ProfileState(
        loading = false,
        error = false,
        content = null
    )

internal fun loadingState() =
    initial().copy(loading = true)

internal fun ProfileState.errorState() =
    copy(loading = false, error = true)

internal fun ProfileState.downloadingDataUpdateState() =
    copy(content = content?.copy(downloadingDataUpdate = true))

internal fun ProfileState.contentState(user: User) =
    copy(
        loading = false,
        content = ContentState(
            user = user,
            firstname = user.firstname ?: "",
            lastname = user.lastname ?: "",
            middlename = user.middlename ?: "",
            city = user.city ?: "",
            phone = user.phone.formatPhone(),
            email = EmailFieldState(
                text = user.email ?: "",
                status = EmailFieldStatus.NotValidated
            ),
            downloadingDataUpdate = false
        )
    )

private fun String.formatPhone(): String =
    replace(Regex("(\\d)(\\d{3})(\\d{3})(\\d{2})(\\d{2})"), "+$1 $2 $3 $4 $5")

internal fun ProfileState.changeFirstnameField(firstname: String) =
    copy(content = content?.copy(firstname = firstname))

internal fun ProfileState.changeLastnameField(lastname: String) =
    copy(content = content?.copy(lastname = lastname))

internal fun ProfileState.changeMiddlenameField(middlename: String) =
    copy(content = content?.copy(middlename = middlename))

internal fun ProfileState.changeCityField(city: String) =
    copy(content = content?.copy(city = city))

internal fun ProfileState.changeEmailField(email: String) =
    copy(
        content = content?.copy(
            email = content.email.copy(
                text = email,
                status = EmailFieldStatus.NotValidated
            )
        )
    )

internal fun ProfileState.validEmailField() =
    copy(
        content = content?.copy(
            email = content.email.copy(
                status = EmailFieldStatus.Valid
            )
        )
    )

internal fun ProfileState.invalidEmailField(reason: EmailValidationError) =
    copy(
        content = content?.copy(
            email = content.email.copy(
                status = EmailFieldStatus.Invalid(reason)
            )
        )
    )

internal fun ProfileState.updateUserSuccessState() =
    copy(content = content?.copy(downloadingDataUpdate = false))

internal fun ProfileState.updateUserErrorState() =
    copy(content = content?.copy(downloadingDataUpdate = false))

internal fun ContentState.getUpdatedUser(): User =
    user.copy(
        firstname = firstname.ifEmpty { null },
        lastname = lastname.ifEmpty { null },
        middlename = middlename.ifEmpty { null },
        email = email.text.ifEmpty { null },
        city = city.ifEmpty { null },
    )
