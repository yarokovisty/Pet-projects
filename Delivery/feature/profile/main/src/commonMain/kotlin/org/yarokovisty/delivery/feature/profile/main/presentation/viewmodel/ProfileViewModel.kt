package org.yarokovisty.delivery.feature.profile.main.presentation.viewmodel

import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.logout.domain.usecase.LogoutUseCase
import org.yarokovisty.delivery.common.profile.main.domain.usecase.GetUserUseCase
import org.yarokovisty.delivery.common.profile.main.domain.usecase.UpdateUserUseCase
import org.yarokovisty.delivery.common.validation.validator.EmailValidator
import org.yarokovisty.delivery.core.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.profile.main.navigation.ProfileRouter
import org.yarokovisty.delivery.feature.profile.main.presentation.event.ProfileEvent
import org.yarokovisty.delivery.feature.profile.main.presentation.intent.ProfileIntent
import org.yarokovisty.delivery.feature.profile.main.presentation.state.ProfileState
import org.yarokovisty.delivery.feature.profile.main.presentation.state.changeCityField
import org.yarokovisty.delivery.feature.profile.main.presentation.state.changeEmailField
import org.yarokovisty.delivery.feature.profile.main.presentation.state.changeFirstnameField
import org.yarokovisty.delivery.feature.profile.main.presentation.state.changeLastnameField
import org.yarokovisty.delivery.feature.profile.main.presentation.state.changeMiddlenameField
import org.yarokovisty.delivery.feature.profile.main.presentation.state.contentState
import org.yarokovisty.delivery.feature.profile.main.presentation.state.downloadingDataUpdateState
import org.yarokovisty.delivery.feature.profile.main.presentation.state.errorState
import org.yarokovisty.delivery.feature.profile.main.presentation.state.getUpdatedUser
import org.yarokovisty.delivery.feature.profile.main.presentation.state.initial
import org.yarokovisty.delivery.feature.profile.main.presentation.state.invalidEmailField
import org.yarokovisty.delivery.feature.profile.main.presentation.state.loadingState
import org.yarokovisty.delivery.feature.profile.main.presentation.state.updateLogoutScreenVisibility
import org.yarokovisty.delivery.feature.profile.main.presentation.state.updateUserErrorState
import org.yarokovisty.delivery.feature.profile.main.presentation.state.updateUserSuccessState
import org.yarokovisty.delivery.feature.profile.main.presentation.state.validEmailField
import org.yarokovisty.delivery.util.phone.PhoneNumberFormatter
import org.yarokovisty.delivery.util.validation.validated.fold

internal class ProfileViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val emailValidator: EmailValidator,
    private val phoneNumberFormatter: PhoneNumberFormatter,
    private val router: ProfileRouter,
) : BaseViewModel<ProfileState, ProfileIntent, ProfileEvent>(initial()) {

    init {
        loadData()
    }

    override fun onIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.ClickCity -> clickCity()
            is ProfileIntent.ClickUpdateData -> updateData()
            is ProfileIntent.CloseLogoutScreen -> changeLogoutScreenVisibility(visible = false)
            is ProfileIntent.ConfirmLogout -> logout()
            is ProfileIntent.InputEmail -> changeEmail(intent.email)
            is ProfileIntent.InputFirstname -> changeFirstname(intent.firstname)
            is ProfileIntent.InputLastname -> changeLastname(intent.lastname)
            is ProfileIntent.InputMiddlename -> changeMiddlename(intent.middlename)
            is ProfileIntent.LoadData -> loadData()
            is ProfileIntent.ShowLogoutScreen -> changeLogoutScreenVisibility(visible = true)
        }
    }

    private fun loadData() {
        updateState { loadingState() }

        launchTrying {
            getUserUseCase()?.let { user ->
                updateState { contentState(user, phoneNumberFormatter) }
            }
        } handle { handleError() }
    }

    private fun handleError() {
        updateState { errorState() }
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

    private fun changeLogoutScreenVisibility(visible: Boolean) {
        updateState { updateLogoutScreenVisibility(visible) }
    }

    private fun logout() {
        updateState { updateLogoutScreenVisibility(false) }

        launch {
            logoutUseCase()
            router.openDeliveryMainTab()
        }
    }
}
