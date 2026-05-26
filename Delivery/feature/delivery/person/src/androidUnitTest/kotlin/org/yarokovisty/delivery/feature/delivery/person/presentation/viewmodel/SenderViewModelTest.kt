package org.yarokovisty.delivery.feature.delivery.person.presentation.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.yarokovisty.delivery.common.delivery.person.domain.entity.PersonInfo
import org.yarokovisty.delivery.common.delivery.person.domain.repository.PersonRepository
import org.yarokovisty.delivery.common.profile.main.domain.entity.User
import org.yarokovisty.delivery.common.profile.main.domain.usecase.GetUserUseCase
import org.yarokovisty.delivery.common.validation.error.NameValidationError
import org.yarokovisty.delivery.common.validation.error.PhoneValidationError
import org.yarokovisty.delivery.common.validation.usecase.RuPhoneValidateUseCase
import org.yarokovisty.delivery.common.validation.validator.NameValidator
import org.yarokovisty.delivery.feature.delivery.person.navigation.PersonScreenType
import org.yarokovisty.delivery.feature.delivery.person.navigation.SenderRouter
import org.yarokovisty.delivery.feature.delivery.person.presentation.intent.PersonIntent
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.NameFieldStatus
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.PhoneFieldStatus
import org.yarokovisty.delivery.util.phone.PhoneNumberFormatter
import org.yarokovisty.delivery.util.unitTest.MainDispatcherRule
import org.yarokovisty.delivery.util.validation.validated.invalid
import org.yarokovisty.delivery.util.validation.validated.valid
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("LargeClass")
internal class SenderViewModelTest {

    private companion object {

        const val MAX_STEPS = 4
        const val TEST_FIRSTNAME = "Ivan"
        const val TEST_LASTNAME = "Ivanov"
        const val TEST_MIDDLENAME = "Ivanovich"
        const val TEST_PHONE = "79123456789"
        const val TEST_PHONE_FORMATTED = "+7 (912) 345-67-89"
    }

    private val personRepository: PersonRepository = mockk(relaxed = true)
    private val getUserUseCase: GetUserUseCase = mockk()
    private val ruPhoneValidateUseCase: RuPhoneValidateUseCase = mockk()
    private val nameValidator: NameValidator = mockk()
    private val phoneNumberFormatter: PhoneNumberFormatter = mockk()
    private val router: SenderRouter = mockk(relaxed = true)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun createViewModel(userResult: User? = null): SenderViewModel {
        coEvery { getUserUseCase() } returns userResult
        return SenderViewModel(
            personRepository = personRepository,
            getUserUseCase = getUserUseCase,
            ruPhoneValidateUseCase = ruPhoneValidateUseCase,
            nameValidator = nameValidator,
            phoneNumberFormatter = phoneNumberFormatter,
            router = router,
            screenType = PersonScreenType.NEW,
            maxSteps = MAX_STEPS,
        )
    }

    @Test
    fun `init EXPECT initial state with step 3`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.stepState

        assertEquals(3, actual.progress)
    }

    @Test
    fun `init EXPECT initial state with max steps`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.stepState

        assertEquals(MAX_STEPS, actual.maxProgress)
    }

    @Test
    fun `init EXPECT empty firstname`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.contentState.firstname.value

        assertEquals("", actual)
    }

    @Test
    fun `init EXPECT empty lastname`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.contentState.lastname.value

        assertEquals("", actual)
    }

    @Test
    fun `init EXPECT empty middlename`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.contentState.middlename

        assertEquals("", actual)
    }

    @Test
    fun `init EXPECT empty phone number`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.contentState.phoneNumber.value

        assertEquals("", actual)
    }

    @Test
    fun `init with user EXPECT firstname loaded from user`() = runTest {
        val user = User(
            id = "1",
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = null,
            city = null,
        )
        every { phoneNumberFormatter.format(TEST_PHONE) } returns TEST_PHONE_FORMATTED
        val viewModel = createViewModel(userResult = user)

        advanceUntilIdle()

        assertEquals(TEST_FIRSTNAME, viewModel.state.value.contentState.firstname.value)
    }

    @Test
    fun `init with user EXPECT lastname loaded from user`() = runTest {
        val user = User(
            id = "1",
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = null,
            city = null,
        )
        every { phoneNumberFormatter.format(TEST_PHONE) } returns TEST_PHONE_FORMATTED
        val viewModel = createViewModel(userResult = user)

        advanceUntilIdle()

        assertEquals(TEST_LASTNAME, viewModel.state.value.contentState.lastname.value)
    }

    @Test
    fun `init with user EXPECT middlename loaded from user`() = runTest {
        val user = User(
            id = "1",
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = null,
            city = null,
        )
        every { phoneNumberFormatter.format(TEST_PHONE) } returns TEST_PHONE_FORMATTED
        val viewModel = createViewModel(userResult = user)

        advanceUntilIdle()

        assertEquals(TEST_MIDDLENAME, viewModel.state.value.contentState.middlename)
    }

    @Test
    fun `init with user EXPECT phone number formatted`() = runTest {
        val user = User(
            id = "1",
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = null,
            city = null,
        )
        every { phoneNumberFormatter.format(TEST_PHONE) } returns TEST_PHONE_FORMATTED
        val viewModel = createViewModel(userResult = user)

        advanceUntilIdle()

        assertEquals(TEST_PHONE_FORMATTED, viewModel.state.value.contentState.phoneNumber.value)
    }

    @Test
    fun `init with user with null firstname EXPECT empty firstname`() = runTest {
        val user = User(
            id = "1",
            phone = TEST_PHONE,
            firstname = null,
            lastname = null,
            middlename = null,
            email = null,
            city = null,
        )
        every { phoneNumberFormatter.format(TEST_PHONE) } returns TEST_PHONE_FORMATTED
        val viewModel = createViewModel(userResult = user)

        advanceUntilIdle()

        assertEquals("", viewModel.state.value.contentState.firstname.value)
    }

    @Test
    fun `init with user with null middlename EXPECT empty middlename`() = runTest {
        val user = User(
            id = "1",
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = null,
            email = null,
            city = null,
        )
        every { phoneNumberFormatter.format(TEST_PHONE) } returns TEST_PHONE_FORMATTED
        val viewModel = createViewModel(userResult = user)

        advanceUntilIdle()

        assertEquals("", viewModel.state.value.contentState.middlename)
    }

    @Test
    fun `init with null user EXPECT state unchanged`() = runTest {
        val viewModel = createViewModel(userResult = null)

        advanceUntilIdle()

        assertEquals("", viewModel.state.value.contentState.firstname.value)
    }

    @Test
    fun `input firstname EXPECT state updated with new firstname`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        advanceUntilIdle()

        assertEquals(TEST_FIRSTNAME, viewModel.state.value.contentState.firstname.value)
    }

    @Test
    fun `input firstname EXPECT firstname field status reset to not validated`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        advanceUntilIdle()

        assertEquals(NameFieldStatus.NotValidated, viewModel.state.value.contentState.firstname.fieldStatus)
    }

    @Test
    fun `input lastname EXPECT state updated with new lastname`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        advanceUntilIdle()

        assertEquals(TEST_LASTNAME, viewModel.state.value.contentState.lastname.value)
    }

    @Test
    fun `input lastname EXPECT lastname field status reset to not validated`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        advanceUntilIdle()

        assertEquals(NameFieldStatus.NotValidated, viewModel.state.value.contentState.lastname.fieldStatus)
    }

    @Test
    fun `input middlename EXPECT state updated with new middlename`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputMiddlename(TEST_MIDDLENAME))
        advanceUntilIdle()

        assertEquals(TEST_MIDDLENAME, viewModel.state.value.contentState.middlename)
    }

    @Test
    fun `input phone number EXPECT state updated with new phone number`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputPhoneNumber(TEST_PHONE))
        advanceUntilIdle()

        assertEquals(TEST_PHONE, viewModel.state.value.contentState.phoneNumber.value)
    }

    @Test
    fun `input phone number EXPECT phone field status reset to not validated`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputPhoneNumber(TEST_PHONE))
        advanceUntilIdle()

        assertEquals(PhoneFieldStatus.NotValidated, viewModel.state.value.contentState.phoneNumber.fieldStatus)
    }

    @Test
    fun `back EXPECT router back called`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.Back)
        advanceUntilIdle()

        verify { router.back() }
    }

    @Test
    fun `click continue with valid data EXPECT firstname field status valid`() = runTest {
        every { nameValidator.validate(TEST_FIRSTNAME) } returns valid(TEST_FIRSTNAME)
        every { nameValidator.validate(TEST_LASTNAME) } returns valid(TEST_LASTNAME)
        every { ruPhoneValidateUseCase(TEST_PHONE) } returns valid(TEST_PHONE)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        viewModel.onIntent(PersonIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(NameFieldStatus.Valid, viewModel.state.value.contentState.firstname.fieldStatus)
    }

    @Test
    fun `click continue with valid data EXPECT lastname field status valid`() = runTest {
        every { nameValidator.validate(TEST_FIRSTNAME) } returns valid(TEST_FIRSTNAME)
        every { nameValidator.validate(TEST_LASTNAME) } returns valid(TEST_LASTNAME)
        every { ruPhoneValidateUseCase(TEST_PHONE) } returns valid(TEST_PHONE)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        viewModel.onIntent(PersonIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(NameFieldStatus.Valid, viewModel.state.value.contentState.lastname.fieldStatus)
    }

    @Test
    fun `click continue with valid data EXPECT phone field status valid`() = runTest {
        every { nameValidator.validate(TEST_FIRSTNAME) } returns valid(TEST_FIRSTNAME)
        every { nameValidator.validate(TEST_LASTNAME) } returns valid(TEST_LASTNAME)
        every { ruPhoneValidateUseCase(TEST_PHONE) } returns valid(TEST_PHONE)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        viewModel.onIntent(PersonIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(PhoneFieldStatus.Valid, viewModel.state.value.contentState.phoneNumber.fieldStatus)
    }

    @Test
    fun `click continue with valid data EXPECT sender saved to repository`() = runTest {
        val expectedPersonInfo = PersonInfo(
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            phone = TEST_PHONE,
        )
        every { nameValidator.validate(TEST_FIRSTNAME) } returns valid(TEST_FIRSTNAME)
        every { nameValidator.validate(TEST_LASTNAME) } returns valid(TEST_LASTNAME)
        every { ruPhoneValidateUseCase(TEST_PHONE) } returns valid(TEST_PHONE)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        viewModel.onIntent(PersonIntent.InputMiddlename(TEST_MIDDLENAME))
        viewModel.onIntent(PersonIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        coVerify { personRepository.setSender(expectedPersonInfo) }
    }

    @Test
    fun `click continue without middlename EXPECT sender saved with null middlename`() = runTest {
        val expectedPersonInfo = PersonInfo(
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = null,
            phone = TEST_PHONE,
        )
        every { nameValidator.validate(TEST_FIRSTNAME) } returns valid(TEST_FIRSTNAME)
        every { nameValidator.validate(TEST_LASTNAME) } returns valid(TEST_LASTNAME)
        every { ruPhoneValidateUseCase(TEST_PHONE) } returns valid(TEST_PHONE)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        viewModel.onIntent(PersonIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        coVerify { personRepository.setSender(expectedPersonInfo) }
    }

    @Test
    fun `click continue with valid data EXPECT router openSenderAddressScreen called`() = runTest {
        every { nameValidator.validate(TEST_FIRSTNAME) } returns valid(TEST_FIRSTNAME)
        every { nameValidator.validate(TEST_LASTNAME) } returns valid(TEST_LASTNAME)
        every { ruPhoneValidateUseCase(TEST_PHONE) } returns valid(TEST_PHONE)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        viewModel.onIntent(PersonIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        verify { router.openSenderAddressScreen() }
    }

    @Test
    fun `click continue with empty firstname EXPECT firstname field status invalid`() = runTest {
        every { nameValidator.validate("") } returns invalid(NameValidationError.EMPTY)
        every { nameValidator.validate(TEST_LASTNAME) } returns valid(TEST_LASTNAME)
        every { ruPhoneValidateUseCase(TEST_PHONE) } returns valid(TEST_PHONE)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        viewModel.onIntent(PersonIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(
            NameFieldStatus.Invalid(NameValidationError.EMPTY),
            viewModel.state.value.contentState.firstname.fieldStatus
        )
    }

    @Test
    fun `click continue with empty firstname EXPECT repository not called`() = runTest {
        every { nameValidator.validate("") } returns invalid(NameValidationError.EMPTY)
        every { nameValidator.validate(TEST_LASTNAME) } returns valid(TEST_LASTNAME)
        every { ruPhoneValidateUseCase(TEST_PHONE) } returns valid(TEST_PHONE)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        viewModel.onIntent(PersonIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        coVerify(exactly = 0) { personRepository.setSender(any()) }
    }

    @Test
    fun `click continue with empty firstname EXPECT router openSenderAddressScreen not called`() = runTest {
        every { nameValidator.validate("") } returns invalid(NameValidationError.EMPTY)
        every { nameValidator.validate(TEST_LASTNAME) } returns valid(TEST_LASTNAME)
        every { ruPhoneValidateUseCase(TEST_PHONE) } returns valid(TEST_PHONE)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        viewModel.onIntent(PersonIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        verify(exactly = 0) { router.openSenderAddressScreen() }
    }

    @Test
    fun `click continue with empty lastname EXPECT lastname field status invalid`() = runTest {
        every { nameValidator.validate(TEST_FIRSTNAME) } returns valid(TEST_FIRSTNAME)
        every { nameValidator.validate("") } returns invalid(NameValidationError.EMPTY)
        every { ruPhoneValidateUseCase(TEST_PHONE) } returns valid(TEST_PHONE)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        viewModel.onIntent(PersonIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(
            NameFieldStatus.Invalid(NameValidationError.EMPTY),
            viewModel.state.value.contentState.lastname.fieldStatus
        )
    }

    @Test
    fun `click continue with empty lastname EXPECT repository not called`() = runTest {
        every { nameValidator.validate(TEST_FIRSTNAME) } returns valid(TEST_FIRSTNAME)
        every { nameValidator.validate("") } returns invalid(NameValidationError.EMPTY)
        every { ruPhoneValidateUseCase(TEST_PHONE) } returns valid(TEST_PHONE)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        viewModel.onIntent(PersonIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        coVerify(exactly = 0) { personRepository.setSender(any()) }
    }

    @Test
    fun `click continue with empty lastname EXPECT router openSenderAddressScreen not called`() = runTest {
        every { nameValidator.validate(TEST_FIRSTNAME) } returns valid(TEST_FIRSTNAME)
        every { nameValidator.validate("") } returns invalid(NameValidationError.EMPTY)
        every { ruPhoneValidateUseCase(TEST_PHONE) } returns valid(TEST_PHONE)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        viewModel.onIntent(PersonIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        verify(exactly = 0) { router.openSenderAddressScreen() }
    }

    @Test
    fun `click continue with empty phone EXPECT phone field status invalid with empty error`() = runTest {
        every { nameValidator.validate(TEST_FIRSTNAME) } returns valid(TEST_FIRSTNAME)
        every { nameValidator.validate(TEST_LASTNAME) } returns valid(TEST_LASTNAME)
        every { ruPhoneValidateUseCase("") } returns invalid(PhoneValidationError.EMPTY)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(
            PhoneFieldStatus.Invalid(PhoneValidationError.EMPTY),
            viewModel.state.value.contentState.phoneNumber.fieldStatus
        )
    }

    @Test
    fun `click continue with invalid phone length EXPECT phone field status invalid with length error`() = runTest {
        val shortPhone = "7912345"
        every { nameValidator.validate(TEST_FIRSTNAME) } returns valid(TEST_FIRSTNAME)
        every { nameValidator.validate(TEST_LASTNAME) } returns valid(TEST_LASTNAME)
        every { ruPhoneValidateUseCase(shortPhone) } returns invalid(PhoneValidationError.INVALID_LENGTH)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        viewModel.onIntent(PersonIntent.InputPhoneNumber(shortPhone))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(
            PhoneFieldStatus.Invalid(PhoneValidationError.INVALID_LENGTH),
            viewModel.state.value.contentState.phoneNumber.fieldStatus
        )
    }

    @Test
    fun `click continue with invalid phone EXPECT repository not called`() = runTest {
        every { nameValidator.validate(TEST_FIRSTNAME) } returns valid(TEST_FIRSTNAME)
        every { nameValidator.validate(TEST_LASTNAME) } returns valid(TEST_LASTNAME)
        every { ruPhoneValidateUseCase("") } returns invalid(PhoneValidationError.EMPTY)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        coVerify(exactly = 0) { personRepository.setSender(any()) }
    }

    @Test
    fun `click continue with invalid phone EXPECT router openSenderAddressScreen not called`() = runTest {
        every { nameValidator.validate(TEST_FIRSTNAME) } returns valid(TEST_FIRSTNAME)
        every { nameValidator.validate(TEST_LASTNAME) } returns valid(TEST_LASTNAME)
        every { ruPhoneValidateUseCase("") } returns invalid(PhoneValidationError.EMPTY)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        verify(exactly = 0) { router.openSenderAddressScreen() }
    }

    @Test
    fun `click continue with all fields invalid EXPECT firstname field status invalid`() = runTest {
        every { nameValidator.validate("") } returns invalid(NameValidationError.EMPTY)
        every { ruPhoneValidateUseCase("") } returns invalid(PhoneValidationError.EMPTY)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(
            NameFieldStatus.Invalid(NameValidationError.EMPTY),
            viewModel.state.value.contentState.firstname.fieldStatus
        )
    }

    @Test
    fun `click continue with all fields invalid EXPECT lastname field status invalid`() = runTest {
        every { nameValidator.validate("") } returns invalid(NameValidationError.EMPTY)
        every { ruPhoneValidateUseCase("") } returns invalid(PhoneValidationError.EMPTY)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(
            NameFieldStatus.Invalid(NameValidationError.EMPTY),
            viewModel.state.value.contentState.lastname.fieldStatus
        )
    }

    @Test
    fun `click continue with all fields invalid EXPECT phone field status invalid`() = runTest {
        every { nameValidator.validate("") } returns invalid(NameValidationError.EMPTY)
        every { ruPhoneValidateUseCase("") } returns invalid(PhoneValidationError.EMPTY)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(
            PhoneFieldStatus.Invalid(PhoneValidationError.EMPTY),
            viewModel.state.value.contentState.phoneNumber.fieldStatus
        )
    }

    @Test
    fun `click continue with all fields invalid EXPECT repository not called`() = runTest {
        every { nameValidator.validate("") } returns invalid(NameValidationError.EMPTY)
        every { ruPhoneValidateUseCase("") } returns invalid(PhoneValidationError.EMPTY)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        coVerify(exactly = 0) { personRepository.setSender(any()) }
    }

    @Test
    fun `click continue with all fields invalid EXPECT router openSenderAddressScreen not called`() = runTest {
        every { nameValidator.validate("") } returns invalid(NameValidationError.EMPTY)
        every { ruPhoneValidateUseCase("") } returns invalid(PhoneValidationError.EMPTY)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        verify(exactly = 0) { router.openSenderAddressScreen() }
    }

    @Test
    fun `input firstname after validation failed EXPECT firstname field status reset to not validated`() = runTest {
        every { nameValidator.validate("") } returns invalid(NameValidationError.EMPTY)
        every { nameValidator.validate(TEST_LASTNAME) } returns valid(TEST_LASTNAME)
        every { ruPhoneValidateUseCase(TEST_PHONE) } returns valid(TEST_PHONE)
        val viewModel = createViewModel()
        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        viewModel.onIntent(PersonIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        advanceUntilIdle()

        assertEquals(NameFieldStatus.NotValidated, viewModel.state.value.contentState.firstname.fieldStatus)
    }

    @Test
    fun `input lastname after validation failed EXPECT lastname field status reset to not validated`() = runTest {
        every { nameValidator.validate(TEST_FIRSTNAME) } returns valid(TEST_FIRSTNAME)
        every { nameValidator.validate("") } returns invalid(NameValidationError.EMPTY)
        every { ruPhoneValidateUseCase(TEST_PHONE) } returns valid(TEST_PHONE)
        val viewModel = createViewModel()
        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        viewModel.onIntent(PersonIntent.InputPhoneNumber(TEST_PHONE))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        advanceUntilIdle()

        assertEquals(NameFieldStatus.NotValidated, viewModel.state.value.contentState.lastname.fieldStatus)
    }

    @Test
    fun `input phone after validation failed EXPECT phone field status reset to not validated`() = runTest {
        every { nameValidator.validate(TEST_FIRSTNAME) } returns valid(TEST_FIRSTNAME)
        every { nameValidator.validate(TEST_LASTNAME) } returns valid(TEST_LASTNAME)
        every { ruPhoneValidateUseCase("") } returns invalid(PhoneValidationError.EMPTY)
        val viewModel = createViewModel()
        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        viewModel.onIntent(PersonIntent.InputPhoneNumber(TEST_PHONE))
        advanceUntilIdle()

        assertEquals(PhoneFieldStatus.NotValidated, viewModel.state.value.contentState.phoneNumber.fieldStatus)
    }

    @Test
    fun `init with user with null lastname EXPECT empty lastname`() = runTest {
        val user = User(
            id = "1",
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = null,
            city = null,
        )
        every { phoneNumberFormatter.format(TEST_PHONE) } returns TEST_PHONE_FORMATTED
        val viewModel = createViewModel(userResult = user)

        advanceUntilIdle()

        assertEquals("", viewModel.state.value.contentState.lastname.value)
    }

    @Test
    fun `init with user EXPECT phoneNumberFormatter format called with user phone`() = runTest {
        val user = User(
            id = "1",
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = null,
            city = null,
        )
        every { phoneNumberFormatter.format(TEST_PHONE) } returns TEST_PHONE_FORMATTED
        val viewModel = createViewModel(userResult = user)

        advanceUntilIdle()

        verify { phoneNumberFormatter.format(TEST_PHONE) }
    }

    @Test
    fun `click continue with formatted phone EXPECT phone validated after clearing`() = runTest {
        val formattedPhone = "+7 (912) 345-67-89"
        val clearedPhone = "79123456789"
        every { nameValidator.validate(TEST_FIRSTNAME) } returns valid(TEST_FIRSTNAME)
        every { nameValidator.validate(TEST_LASTNAME) } returns valid(TEST_LASTNAME)
        every { ruPhoneValidateUseCase(clearedPhone) } returns valid(clearedPhone)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.InputFirstname(TEST_FIRSTNAME))
        viewModel.onIntent(PersonIntent.InputLastname(TEST_LASTNAME))
        viewModel.onIntent(PersonIntent.InputPhoneNumber(formattedPhone))
        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(PhoneFieldStatus.Valid, viewModel.state.value.contentState.phoneNumber.fieldStatus)
    }

    @Test
    fun `click continue with all fields invalid EXPECT all validators called`() = runTest {
        every { nameValidator.validate("") } returns invalid(NameValidationError.EMPTY)
        every { ruPhoneValidateUseCase("") } returns invalid(PhoneValidationError.EMPTY)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        verify(exactly = 2) { nameValidator.validate("") }
    }

    @Test
    fun `click continue with all fields invalid EXPECT phone validator called`() = runTest {
        every { nameValidator.validate("") } returns invalid(NameValidationError.EMPTY)
        every { ruPhoneValidateUseCase("") } returns invalid(PhoneValidationError.EMPTY)
        val viewModel = createViewModel()

        viewModel.onIntent(PersonIntent.ClickContinue)
        advanceUntilIdle()

        verify { ruPhoneValidateUseCase("") }
    }

    @Test
    fun `init EXPECT firstname field status not validated`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.contentState.firstname.fieldStatus

        assertEquals(NameFieldStatus.NotValidated, actual)
    }

    @Test
    fun `init EXPECT lastname field status not validated`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.contentState.lastname.fieldStatus

        assertEquals(NameFieldStatus.NotValidated, actual)
    }

    @Test
    fun `init EXPECT phone field status not validated`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.contentState.phoneNumber.fieldStatus

        assertEquals(PhoneFieldStatus.NotValidated, actual)
    }
}
