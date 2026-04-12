package org.yarokovisty.delivery.feature.profile.main.impl.presentation.viewmodel

import org.yarokovisty.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.validation.validator.EmailValidator
import org.yarokovisty.delivery.core.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.profile.main.api.domain.entity.User
import org.yarokovisty.delivery.feature.profile.main.impl.domain.usecase.GetUserUseCase
import org.yarokovisty.delivery.feature.profile.main.impl.domain.usecase.UpdateUserUseCase
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.event.ProfileEvent
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.intent.ProfileIntent
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.router.ProfileRouter
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.ContentState
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.ProfileState
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.changeCityField
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.changeEmailField
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.changeFirstnameField
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.changeLastnameField
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.changeMiddlenameField
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.contentState
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.downloadingDataUpdateState
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.errorState
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.initial
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.invalidEmailField
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.loadingState
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.updateUserErrorState
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.updateUserSuccessState
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.validEmailField
import org.yarokovisty.delivery.util.validation.validated.fold
import kotlin.text.ifEmpty

internal class ProfileViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val emailValidator: EmailValidator,
    private val router: ProfileRouter,
) : BaseViewModel<ProfileState, ProfileIntent, ProfileEvent>(initial()) {

    init {
        loadData()
    }

    private fun loadData() {
        updateState { loadingState() }

        launchTrying {
            val user = getUserUseCase()
            updateState { contentState(user) }
        } handle { handleError() }
    }

    private fun handleError() {
        updateState { errorState() }
    }

    override fun onIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.ClickCity -> clickCity()
            is ProfileIntent.ClickUpdateData -> updateData()
            is ProfileIntent.InputEmail -> changeEmail(intent.email)
            is ProfileIntent.InputFirstname -> changeFirstname(intent.firstname)
            is ProfileIntent.InputLastname -> changeLastname(intent.lastname)
            is ProfileIntent.InputMiddlename -> changeMiddlename(intent.middlename)
            is ProfileIntent.LoadData -> loadData()
        }
    }

    private fun clickCity() {
        launch {
            val point = awaitResult<DeliveryPoint>()
            updateState { changeCityField(point.name) }
        }
        router.openDirectionScreen()
    }

    private fun updateData() {
        val content = stateValue.content ?: return
        val email = content.email.text

        if (!validateEmail(email)) return

        updateState { downloadingDataUpdateState() }

        launchTrying {
            val updatedUser = content.getUpdatedUser()
            updateUserUseCase(updatedUser)
            emitEvent(ProfileEvent.UpdateUserDataSuccess)
            updateState { updateUserSuccessState() }
        } handle { handleUpdateUserError() }
    }

    private fun validateEmail(email: String): Boolean =
        emailValidator.validate(email, required = true)
            .fold(
                onValid = {
                    updateState { validEmailField() }
                    true
                },
                onInvalid = { reason ->
                    updateState { invalidEmailField(reason) }
                    false
                }
            )

    private fun ContentState.getUpdatedUser(): User =
        user.copy(
            firstname = firstname.ifEmpty { null },
            lastname = lastname.ifEmpty { null },
            middlename = middlename.ifEmpty { null },
            email = email.text.ifEmpty { null },
            city = city.ifEmpty { null },
        )

    private fun handleUpdateUserError() {
        updateState { updateUserErrorState() }

        launch {
            emitEvent(ProfileEvent.UpdateUserDataError)
        }
    }

    private fun changeEmail(email: String) {
        updateState { changeEmailField(email) }
    }

    private fun changeFirstname(firstname: String) {
        updateState { changeFirstnameField(firstname) }
    }

    private fun changeLastname(lastname: String) {
        updateState { changeLastnameField(lastname) }
    }

    private fun changeMiddlename(middlename: String) {
        updateState { changeMiddlenameField(middlename) }
    }
}
