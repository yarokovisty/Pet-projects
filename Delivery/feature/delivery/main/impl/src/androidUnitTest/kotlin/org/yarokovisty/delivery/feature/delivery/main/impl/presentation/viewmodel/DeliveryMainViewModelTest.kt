package org.yarokovisty.delivery.feature.delivery.main.impl.presentation.viewmodel

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.PackageType
import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.ParcelType
import org.yarokovisty.delivery.feature.delivery.main.api.domain.repository.DeliveryRepository
import org.yarokovisty.delivery.feature.delivery.main.impl.domain.usecase.GetAlternativeDeliveryPointsUseCase
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.intent.DeliveryMainIntent
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.DeliveryCalculatorContent
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.DeliveryMainState
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.TrackerContent
import org.yarokovisty.delivery.feature.direction.api.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.feature.direction.api.domain.repository.DirectionRepository
import kotlin.test.assertEquals

class MainDispatcherRule(
    val dispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

class DeliveryMainViewModelTest {

    private companion object {
        const val ALTERNATIVE_POINTS_LIMIT = 3
    }

    private val deliveryRepository: DeliveryRepository = mockk()
    private val directionRepository: DirectionRepository = mockk()
    private val getAlternativeDeliveryPointsUseCase: GetAlternativeDeliveryPointsUseCase = mockk()

    private val points = listOf(
        DeliveryPoint(
            id = "0",
            name = "name0",
            latitude = 0.0,
            longitude = 0.0
        ),
        DeliveryPoint(
            id = "1",
            name = "name1",
            latitude = 1.0,
            longitude = 1.0
        ),
        DeliveryPoint(
            id = "2",
            name = "name2",
            latitude = 2.0,
            longitude = 2.0
        )
    )
    val alternativePoints = points.take(ALTERNATIVE_POINTS_LIMIT)
    private val parcelTypes = listOf(
        ParcelType(
            id = "envelope",
            type = PackageType.ENVELOPE,
            name = "name0",
            length = 1,
            width = 1,
            height = 1,
            weight = 1,
        ),
        ParcelType(
            id = "box-s",
            type = PackageType.BOX_S,
            name = "name1",
            length = 2,
            width = 2,
            height = 2,
            weight = 2,
        )
    )

    private fun createViewModel() =
        DeliveryMainViewModel(
            deliveryRepository,
            directionRepository,
            getAlternativeDeliveryPointsUseCase
        )

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `init EXPECT loading state`() = runTest {
        val expected = DeliveryMainState.INITIAL.copy(loading = true)

        val viewModel = createViewModel()

        val actual = viewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `loading data is success EXPECT content state`() = runTest {
        val alternativePointsUI = alternativePoints.map { it.name }
        val expected = DeliveryMainState.INITIAL.copy(
            deliveryCalculatorContent = DeliveryCalculatorContent(
                points = points,
                selectedPointFrom = null,
                alternativePointsFrom = alternativePointsUI,
                selectedPointTo = null,
                alternativePointsTo = alternativePointsUI,
                parcelTypes = parcelTypes,
                selectedParcelType = null
            )
        )
        coEvery { directionRepository.getDeliveryPoints() } returns points
        coEvery { deliveryRepository.getParcelTypes() } returns parcelTypes
        every { getAlternativeDeliveryPointsUseCase(points) } returns alternativePoints

        val viewModel = createViewModel()
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `loading delivery points is error EXPECT error state`() = runTest {
        val expected = DeliveryMainState.INITIAL.copy(error = true)
        coEvery { directionRepository.getDeliveryPoints() } throws Exception("error")

        val viewModel = createViewModel()
        advanceUntilIdle()


        val actual = viewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `loading parcel types is error EXPECT error state`() = runTest {
        val expected = DeliveryMainState.INITIAL.copy(error = true)
        coEvery { directionRepository.getDeliveryPoints() } returns points
        coEvery { deliveryRepository.getParcelTypes() } throws Exception("error")

        val viewModel = createViewModel()
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `change input parcel id EXPECT updated input id parcel`() = runTest {
        val alternativePointsUI = alternativePoints.map { it.name }
        val expected = DeliveryMainState.INITIAL.copy(
            deliveryCalculatorContent = DeliveryCalculatorContent(
                points = points,
                selectedPointFrom = null,
                alternativePointsFrom = alternativePointsUI,
                selectedPointTo = null,
                alternativePointsTo = alternativePointsUI,
                parcelTypes = parcelTypes,
                selectedParcelType = null
            ),
            trackerContent = TrackerContent("1")
        )
        val intent = DeliveryMainIntent.ChangeInputParcelId("1")
        coEvery { directionRepository.getDeliveryPoints() } returns points
        coEvery { deliveryRepository.getParcelTypes() } returns parcelTypes
        every { getAlternativeDeliveryPointsUseCase(points) } returns alternativePoints

        val viewModel = createViewModel()
        viewModel.onIntent(intent)
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertEquals(expected, actual)
    }
}