package org.yarokovisty.delivery.feature.delivery.point.presentation.viewmodel

import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.yarokovisty.delivery.common.delivery.point.domain.entity.Address
import org.yarokovisty.delivery.common.delivery.point.domain.repository.AddressRepository
import org.yarokovisty.delivery.common.validation.error.AddressValidationError
import org.yarokovisty.delivery.common.validation.validator.AddressValidator
import org.yarokovisty.delivery.feature.delivery.point.navigation.SenderAddressRouter
import org.yarokovisty.delivery.feature.delivery.point.presentation.intent.SenderAddressIntent
import org.yarokovisty.delivery.feature.delivery.point.presentation.state.FieldStatus
import org.yarokovisty.delivery.util.unitTest.MainDispatcherRule
import org.yarokovisty.delivery.util.validation.validated.invalid
import org.yarokovisty.delivery.util.validation.validated.valid
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
internal class SenderAddressViewModelTest {

    private companion object {

        const val MAX_STEPS = 6
        const val TEST_STREET = "Lenina"
        const val TEST_HOUSE = "10"
        const val TEST_APARTMENT = "42"
        const val TEST_COMMENT = "Ring the doorbell"
    }

    private val addressRepository: AddressRepository = mockk(relaxed = true)
    private val addressValidator: AddressValidator = mockk()
    private val router: SenderAddressRouter = mockk(relaxed = true)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun createViewModel(): SenderAddressViewModel =
        SenderAddressViewModel(
            addressRepository = addressRepository,
            addressValidator = addressValidator,
            router = router,
            maxSteps = MAX_STEPS,
        )

    // region Init

    @Test
    fun `init EXPECT initial state with step 4`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.stepState

        assertEquals(4, actual.progress)
    }

    @Test
    fun `init EXPECT initial state with max steps`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.stepState

        assertEquals(MAX_STEPS, actual.maxProgress)
    }

    @Test
    fun `init EXPECT empty street`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.contentState.street.value

        assertEquals("", actual)
    }

    @Test
    fun `init EXPECT empty house`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.contentState.house.value

        assertEquals("", actual)
    }

    @Test
    fun `init EXPECT empty apartment`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.contentState.apartment.value

        assertEquals("", actual)
    }

    @Test
    fun `init EXPECT empty comment`() {
        val viewModel = createViewModel()

        val actual = viewModel.state.value.contentState.comment

        assertEquals("", actual)
    }

    // endregion

    // region Input

    @Test
    fun `input street EXPECT state updated with new street`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.InputStreet(TEST_STREET))
        advanceUntilIdle()

        assertEquals(TEST_STREET, viewModel.state.value.contentState.street.value)
    }

    @Test
    fun `input street EXPECT street field status reset to not validated`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.InputStreet(TEST_STREET))
        advanceUntilIdle()

        assertEquals(FieldStatus.NotValidated, viewModel.state.value.contentState.street.fieldStatus)
    }

    @Test
    fun `input house EXPECT state updated with new house`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.InputHouse(TEST_HOUSE))
        advanceUntilIdle()

        assertEquals(TEST_HOUSE, viewModel.state.value.contentState.house.value)
    }

    @Test
    fun `input house EXPECT house field status reset to not validated`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.InputHouse(TEST_HOUSE))
        advanceUntilIdle()

        assertEquals(FieldStatus.NotValidated, viewModel.state.value.contentState.house.fieldStatus)
    }

    @Test
    fun `input apartment EXPECT state updated with new apartment`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.InputApartment(TEST_APARTMENT))
        advanceUntilIdle()

        assertEquals(TEST_APARTMENT, viewModel.state.value.contentState.apartment.value)
    }

    @Test
    fun `input apartment EXPECT apartment field status reset to not validated`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.InputApartment(TEST_APARTMENT))
        advanceUntilIdle()

        assertEquals(FieldStatus.NotValidated, viewModel.state.value.contentState.apartment.fieldStatus)
    }

    @Test
    fun `input comment EXPECT state updated with new comment`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.InputComment(TEST_COMMENT))
        advanceUntilIdle()

        assertEquals(TEST_COMMENT, viewModel.state.value.contentState.comment)
    }

    // endregion

    // region Back

    @Test
    fun `back EXPECT router back called`() = runTest {
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.Back)
        advanceUntilIdle()

        verify { router.back() }
    }

    // endregion

    // region Click Continue - Valid Data

    @Test
    fun `click continue with valid data EXPECT street field status valid`() = runTest {
        every { addressValidator.validate(TEST_STREET) } returns valid(TEST_STREET)
        every { addressValidator.validate(TEST_HOUSE) } returns valid(TEST_HOUSE)
        every { addressValidator.validate(TEST_APARTMENT) } returns valid(TEST_APARTMENT)
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.InputStreet(TEST_STREET))
        viewModel.onIntent(SenderAddressIntent.InputHouse(TEST_HOUSE))
        viewModel.onIntent(SenderAddressIntent.InputApartment(TEST_APARTMENT))
        viewModel.onIntent(SenderAddressIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(FieldStatus.Valid, viewModel.state.value.contentState.street.fieldStatus)
    }

    @Test
    fun `click continue with valid data EXPECT house field status valid`() = runTest {
        every { addressValidator.validate(TEST_STREET) } returns valid(TEST_STREET)
        every { addressValidator.validate(TEST_HOUSE) } returns valid(TEST_HOUSE)
        every { addressValidator.validate(TEST_APARTMENT) } returns valid(TEST_APARTMENT)
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.InputStreet(TEST_STREET))
        viewModel.onIntent(SenderAddressIntent.InputHouse(TEST_HOUSE))
        viewModel.onIntent(SenderAddressIntent.InputApartment(TEST_APARTMENT))
        viewModel.onIntent(SenderAddressIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(FieldStatus.Valid, viewModel.state.value.contentState.house.fieldStatus)
    }

    @Test
    fun `click continue with valid data EXPECT apartment field status valid`() = runTest {
        every { addressValidator.validate(TEST_STREET) } returns valid(TEST_STREET)
        every { addressValidator.validate(TEST_HOUSE) } returns valid(TEST_HOUSE)
        every { addressValidator.validate(TEST_APARTMENT) } returns valid(TEST_APARTMENT)
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.InputStreet(TEST_STREET))
        viewModel.onIntent(SenderAddressIntent.InputHouse(TEST_HOUSE))
        viewModel.onIntent(SenderAddressIntent.InputApartment(TEST_APARTMENT))
        viewModel.onIntent(SenderAddressIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(FieldStatus.Valid, viewModel.state.value.contentState.apartment.fieldStatus)
    }

    @Test
    fun `click continue with valid data EXPECT sender address saved to repository`() = runTest {
        val expectedAddress = Address(
            street = TEST_STREET,
            house = TEST_HOUSE,
            apartment = TEST_APARTMENT,
            comment = TEST_COMMENT,
            nonContacted = null,
        )
        every { addressValidator.validate(TEST_STREET) } returns valid(TEST_STREET)
        every { addressValidator.validate(TEST_HOUSE) } returns valid(TEST_HOUSE)
        every { addressValidator.validate(TEST_APARTMENT) } returns valid(TEST_APARTMENT)
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.InputStreet(TEST_STREET))
        viewModel.onIntent(SenderAddressIntent.InputHouse(TEST_HOUSE))
        viewModel.onIntent(SenderAddressIntent.InputApartment(TEST_APARTMENT))
        viewModel.onIntent(SenderAddressIntent.InputComment(TEST_COMMENT))
        viewModel.onIntent(SenderAddressIntent.ClickContinue)
        advanceUntilIdle()

        coVerify { addressRepository.setSender(expectedAddress) }
    }

    @Test
    fun `click continue with valid data and empty comment EXPECT sender saved with empty comment`() = runTest {
        val expectedAddress = Address(
            street = TEST_STREET,
            house = TEST_HOUSE,
            apartment = TEST_APARTMENT,
            comment = "",
            nonContacted = null,
        )
        every { addressValidator.validate(TEST_STREET) } returns valid(TEST_STREET)
        every { addressValidator.validate(TEST_HOUSE) } returns valid(TEST_HOUSE)
        every { addressValidator.validate(TEST_APARTMENT) } returns valid(TEST_APARTMENT)
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.InputStreet(TEST_STREET))
        viewModel.onIntent(SenderAddressIntent.InputHouse(TEST_HOUSE))
        viewModel.onIntent(SenderAddressIntent.InputApartment(TEST_APARTMENT))
        viewModel.onIntent(SenderAddressIntent.ClickContinue)
        advanceUntilIdle()

        coVerify { addressRepository.setSender(expectedAddress) }
    }

    // endregion

    // region Click Continue - Invalid Street

    @Test
    fun `click continue with empty street EXPECT street field status invalid`() = runTest {
        every { addressValidator.validate("") } returns invalid(AddressValidationError.EMPTY)
        every { addressValidator.validate(TEST_HOUSE) } returns valid(TEST_HOUSE)
        every { addressValidator.validate(TEST_APARTMENT) } returns valid(TEST_APARTMENT)
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.InputHouse(TEST_HOUSE))
        viewModel.onIntent(SenderAddressIntent.InputApartment(TEST_APARTMENT))
        viewModel.onIntent(SenderAddressIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(
            FieldStatus.Invalid(AddressValidationError.EMPTY),
            viewModel.state.value.contentState.street.fieldStatus
        )
    }

    @Test
    fun `click continue with empty street EXPECT repository not called`() = runTest {
        every { addressValidator.validate("") } returns invalid(AddressValidationError.EMPTY)
        every { addressValidator.validate(TEST_HOUSE) } returns valid(TEST_HOUSE)
        every { addressValidator.validate(TEST_APARTMENT) } returns valid(TEST_APARTMENT)
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.InputHouse(TEST_HOUSE))
        viewModel.onIntent(SenderAddressIntent.InputApartment(TEST_APARTMENT))
        viewModel.onIntent(SenderAddressIntent.ClickContinue)
        advanceUntilIdle()

        coVerify(exactly = 0) { addressRepository.setSender(any()) }
    }

    // endregion

    // region Click Continue - Invalid House

    @Test
    fun `click continue with empty house EXPECT house field status invalid`() = runTest {
        every { addressValidator.validate(TEST_STREET) } returns valid(TEST_STREET)
        every { addressValidator.validate("") } returns invalid(AddressValidationError.EMPTY)
        every { addressValidator.validate(TEST_APARTMENT) } returns valid(TEST_APARTMENT)
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.InputStreet(TEST_STREET))
        viewModel.onIntent(SenderAddressIntent.InputApartment(TEST_APARTMENT))
        viewModel.onIntent(SenderAddressIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(
            FieldStatus.Invalid(AddressValidationError.EMPTY),
            viewModel.state.value.contentState.house.fieldStatus
        )
    }

    @Test
    fun `click continue with empty house EXPECT repository not called`() = runTest {
        every { addressValidator.validate(TEST_STREET) } returns valid(TEST_STREET)
        every { addressValidator.validate("") } returns invalid(AddressValidationError.EMPTY)
        every { addressValidator.validate(TEST_APARTMENT) } returns valid(TEST_APARTMENT)
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.InputStreet(TEST_STREET))
        viewModel.onIntent(SenderAddressIntent.InputApartment(TEST_APARTMENT))
        viewModel.onIntent(SenderAddressIntent.ClickContinue)
        advanceUntilIdle()

        coVerify(exactly = 0) { addressRepository.setSender(any()) }
    }

    // endregion

    // region Click Continue - Invalid Apartment

    @Test
    fun `click continue with empty apartment EXPECT apartment field status invalid`() = runTest {
        every { addressValidator.validate(TEST_STREET) } returns valid(TEST_STREET)
        every { addressValidator.validate(TEST_HOUSE) } returns valid(TEST_HOUSE)
        every { addressValidator.validate("") } returns invalid(AddressValidationError.EMPTY)
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.InputStreet(TEST_STREET))
        viewModel.onIntent(SenderAddressIntent.InputHouse(TEST_HOUSE))
        viewModel.onIntent(SenderAddressIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(
            FieldStatus.Invalid(AddressValidationError.EMPTY),
            viewModel.state.value.contentState.apartment.fieldStatus
        )
    }

    @Test
    fun `click continue with empty apartment EXPECT repository not called`() = runTest {
        every { addressValidator.validate(TEST_STREET) } returns valid(TEST_STREET)
        every { addressValidator.validate(TEST_HOUSE) } returns valid(TEST_HOUSE)
        every { addressValidator.validate("") } returns invalid(AddressValidationError.EMPTY)
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.InputStreet(TEST_STREET))
        viewModel.onIntent(SenderAddressIntent.InputHouse(TEST_HOUSE))
        viewModel.onIntent(SenderAddressIntent.ClickContinue)
        advanceUntilIdle()

        coVerify(exactly = 0) { addressRepository.setSender(any()) }
    }

    // endregion

    // region Click Continue - All Fields Invalid

    @Test
    fun `click continue with all fields empty EXPECT street field status invalid`() = runTest {
        every { addressValidator.validate("") } returns invalid(AddressValidationError.EMPTY)
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(
            FieldStatus.Invalid(AddressValidationError.EMPTY),
            viewModel.state.value.contentState.street.fieldStatus
        )
    }

    @Test
    fun `click continue with all fields empty EXPECT house field status invalid`() = runTest {
        every { addressValidator.validate("") } returns invalid(AddressValidationError.EMPTY)
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(
            FieldStatus.Invalid(AddressValidationError.EMPTY),
            viewModel.state.value.contentState.house.fieldStatus
        )
    }

    @Test
    fun `click continue with all fields empty EXPECT apartment field status invalid`() = runTest {
        every { addressValidator.validate("") } returns invalid(AddressValidationError.EMPTY)
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.ClickContinue)
        advanceUntilIdle()

        assertEquals(
            FieldStatus.Invalid(AddressValidationError.EMPTY),
            viewModel.state.value.contentState.apartment.fieldStatus
        )
    }

    @Test
    fun `click continue with all fields empty EXPECT repository not called`() = runTest {
        every { addressValidator.validate("") } returns invalid(AddressValidationError.EMPTY)
        val viewModel = createViewModel()

        viewModel.onIntent(SenderAddressIntent.ClickContinue)
        advanceUntilIdle()

        coVerify(exactly = 0) { addressRepository.setSender(any()) }
    }

    // endregion
}
