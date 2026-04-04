package org.yarokovisty.delivery.feature.delivery.main.impl.presentation.viewmodel

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.PackageType
import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.ParcelType
import org.yarokovisty.delivery.feature.delivery.main.api.domain.repository.DeliveryRepository
import org.yarokovisty.delivery.feature.delivery.main.impl.domain.usecase.GetAlternativeDeliveryPointsUseCase
import org.yarokovisty.delivery.feature.delivery.main.impl.domain.usecase.GetDeliveryPointByNameUseCase
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.intent.DeliveryMainIntent
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.router.DeliveryRouter
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.DeliveryCalculatorContent
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.TrackerContent
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.initial
import org.yarokovisty.delivery.feature.direction.api.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.feature.direction.api.domain.entity.DirectionType
import org.yarokovisty.delivery.feature.direction.api.domain.repository.DirectionRepository
import org.yarokovisty.delivery.util.unitTest.MainDispatcherRule
import kotlin.test.assertEquals

class DeliveryMainViewModelTest {

    private companion object {
        const val ALTERNATIVE_POINTS_LIMIT = 3
    }

    private val deliveryRepository: DeliveryRepository = mockk()
    private val directionRepository: DirectionRepository = mockk()
    private val getAlternativeDeliveryPointsUseCase: GetAlternativeDeliveryPointsUseCase = mockk()
    private val getDeliveryPointByNameUseCase: GetDeliveryPointByNameUseCase = mockk()
    private val router = mockk<DeliveryRouter>(relaxed = true)

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
            getAlternativeDeliveryPointsUseCase,
            getDeliveryPointByNameUseCase,
            router
        )

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `init EXPECT loading state`() = runTest {
        val expected = initial().copy(loading = true)

        val viewModel = createViewModel()

        val actual = viewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `loading data is success EXPECT content state`() = runTest {
        val alternativePointsUI = alternativePoints.map { it.name }
        val expected = initial().copy(
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
        val expected = initial().copy(error = true)
        coEvery { directionRepository.getDeliveryPoints() } throws Exception("error")

        val viewModel = createViewModel()
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `loading parcel types is error EXPECT error state`() = runTest {
        val expected = initial().copy(error = true)
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
        val expected = initial().copy(
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

    @Test
    fun `select alternative delivery point from EXPECT selected point from updated`() = runTest {
        val selectedPoint = points[1]
        val alternativePointsUI = alternativePoints.map { it.name }
        val expected = initial().copy(
            deliveryCalculatorContent = DeliveryCalculatorContent(
                points = points,
                selectedPointFrom = selectedPoint,
                alternativePointsFrom = alternativePointsUI,
                selectedPointTo = null,
                alternativePointsTo = alternativePointsUI,
                parcelTypes = parcelTypes,
                selectedParcelType = null
            )
        )
        val intent = DeliveryMainIntent.SelectAlternativeDeliveryPointFrom(selectedPoint.name)
        coEvery { directionRepository.getDeliveryPoints() } returns points
        coEvery { deliveryRepository.getParcelTypes() } returns parcelTypes
        every { getAlternativeDeliveryPointsUseCase(points) } returns alternativePoints
        coEvery { getDeliveryPointByNameUseCase(points, selectedPoint.name) } returns selectedPoint

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(intent)
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `select alternative delivery point to EXPECT selected point to updated`() = runTest {
        val selectedPoint = points[2]
        val alternativePointsUI = alternativePoints.map { it.name }
        val expected = initial().copy(
            deliveryCalculatorContent = DeliveryCalculatorContent(
                points = points,
                selectedPointFrom = null,
                alternativePointsFrom = alternativePointsUI,
                selectedPointTo = selectedPoint,
                alternativePointsTo = alternativePointsUI,
                parcelTypes = parcelTypes,
                selectedParcelType = null
            )
        )
        val intent = DeliveryMainIntent.SelectAlternativeDeliveryPointTo(selectedPoint.name)
        coEvery { directionRepository.getDeliveryPoints() } returns points
        coEvery { deliveryRepository.getParcelTypes() } returns parcelTypes
        every { getAlternativeDeliveryPointsUseCase(points) } returns alternativePoints
        coEvery { getDeliveryPointByNameUseCase(points, selectedPoint.name) } returns selectedPoint

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(intent)
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `select delivery point from EXPECT router opens direction screen with FROM type`() = runTest {
        val intent = DeliveryMainIntent.SelectDeliveryPointFrom
        coEvery { directionRepository.getDeliveryPoints() } returns points
        coEvery { deliveryRepository.getParcelTypes() } returns parcelTypes
        every { getAlternativeDeliveryPointsUseCase(points) } returns alternativePoints

        val viewModel = createViewModel()
        viewModel.onIntent(intent)
        advanceUntilIdle()

        verify { router.openDirectionScreen(DirectionType.FROM) }
    }

    @Test
    fun `select delivery point to EXPECT router opens direction screen with TO type`() = runTest {
        val intent = DeliveryMainIntent.SelectDeliveryPointTo
        coEvery { directionRepository.getDeliveryPoints() } returns points
        coEvery { deliveryRepository.getParcelTypes() } returns parcelTypes
        every { getAlternativeDeliveryPointsUseCase(points) } returns alternativePoints

        val viewModel = createViewModel()
        viewModel.onIntent(intent)
        advanceUntilIdle()

        verify { router.openDirectionScreen(DirectionType.TO) }
    }

    @Test
    fun `open parcel type screen EXPECT showSelectParcelType is true`() = runTest {
        val alternativePointsUI = alternativePoints.map { it.name }
        val expected = initial().copy(
            deliveryCalculatorContent = DeliveryCalculatorContent(
                points = points,
                selectedPointFrom = null,
                alternativePointsFrom = alternativePointsUI,
                selectedPointTo = null,
                alternativePointsTo = alternativePointsUI,
                parcelTypes = parcelTypes,
                selectedParcelType = null
            ),
            showSelectParcelType = true
        )
        val intent = DeliveryMainIntent.OpenParcelTypeScreen
        coEvery { directionRepository.getDeliveryPoints() } returns points
        coEvery { deliveryRepository.getParcelTypes() } returns parcelTypes
        every { getAlternativeDeliveryPointsUseCase(points) } returns alternativePoints

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(intent)
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `close parcel type screen EXPECT showSelectParcelType is false`() = runTest {
        val alternativePointsUI = alternativePoints.map { it.name }
        val expected = initial().copy(
            deliveryCalculatorContent = DeliveryCalculatorContent(
                points = points,
                selectedPointFrom = null,
                alternativePointsFrom = alternativePointsUI,
                selectedPointTo = null,
                alternativePointsTo = alternativePointsUI,
                parcelTypes = parcelTypes,
                selectedParcelType = null
            ),
            showSelectParcelType = false
        )
        coEvery { directionRepository.getDeliveryPoints() } returns points
        coEvery { deliveryRepository.getParcelTypes() } returns parcelTypes
        every { getAlternativeDeliveryPointsUseCase(points) } returns alternativePoints

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(DeliveryMainIntent.OpenParcelTypeScreen)
        viewModel.onIntent(DeliveryMainIntent.CloseParcelTypeScreen)
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `select parcel type EXPECT selected parcel type updated and screen closed`() = runTest {
        val selectedParcelType = parcelTypes[1]
        val alternativePointsUI = alternativePoints.map { it.name }
        val expected = initial().copy(
            deliveryCalculatorContent = DeliveryCalculatorContent(
                points = points,
                selectedPointFrom = null,
                alternativePointsFrom = alternativePointsUI,
                selectedPointTo = null,
                alternativePointsTo = alternativePointsUI,
                parcelTypes = parcelTypes,
                selectedParcelType = selectedParcelType
            ),
            showSelectParcelType = false
        )
        val intent = DeliveryMainIntent.SelectParcelType(selectedParcelType)
        coEvery { directionRepository.getDeliveryPoints() } returns points
        coEvery { deliveryRepository.getParcelTypes() } returns parcelTypes
        every { getAlternativeDeliveryPointsUseCase(points) } returns alternativePoints

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(DeliveryMainIntent.OpenParcelTypeScreen)
        viewModel.onIntent(intent)
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `only point from selected EXPECT calculateButtonEnabled is false`() = runTest {
        val selectedPoint = points[0]
        coEvery { directionRepository.getDeliveryPoints() } returns points
        coEvery { deliveryRepository.getParcelTypes() } returns parcelTypes
        every { getAlternativeDeliveryPointsUseCase(points) } returns alternativePoints
        coEvery { getDeliveryPointByNameUseCase(points, selectedPoint.name) } returns selectedPoint

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(DeliveryMainIntent.SelectAlternativeDeliveryPointFrom(selectedPoint.name))
        advanceUntilIdle()

        val actual = viewModel.state.value.deliveryCalculatorContent?.calculateButtonEnabled
        assertEquals(false, actual)
    }

    @Test
    fun `only point to selected EXPECT calculateButtonEnabled is false`() = runTest {
        val selectedPoint = points[1]
        coEvery { directionRepository.getDeliveryPoints() } returns points
        coEvery { deliveryRepository.getParcelTypes() } returns parcelTypes
        every { getAlternativeDeliveryPointsUseCase(points) } returns alternativePoints
        coEvery { getDeliveryPointByNameUseCase(points, selectedPoint.name) } returns selectedPoint

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(DeliveryMainIntent.SelectAlternativeDeliveryPointTo(selectedPoint.name))
        advanceUntilIdle()

        val actual = viewModel.state.value.deliveryCalculatorContent?.calculateButtonEnabled
        assertEquals(false, actual)
    }

    @Test
    fun `only parcel type selected EXPECT calculateButtonEnabled is false`() = runTest {
        val selectedParcelType = parcelTypes[0]
        coEvery { directionRepository.getDeliveryPoints() } returns points
        coEvery { deliveryRepository.getParcelTypes() } returns parcelTypes
        every { getAlternativeDeliveryPointsUseCase(points) } returns alternativePoints

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(DeliveryMainIntent.SelectParcelType(selectedParcelType))
        advanceUntilIdle()

        val actual = viewModel.state.value.deliveryCalculatorContent?.calculateButtonEnabled
        assertEquals(false, actual)
    }

    @Test
    fun `point from and to selected but no parcel type EXPECT calculateButtonEnabled is false`() = runTest {
        val selectedPointFrom = points[0]
        val selectedPointTo = points[1]
        coEvery { directionRepository.getDeliveryPoints() } returns points
        coEvery { deliveryRepository.getParcelTypes() } returns parcelTypes
        every { getAlternativeDeliveryPointsUseCase(points) } returns alternativePoints
        coEvery { getDeliveryPointByNameUseCase(points, selectedPointFrom.name) } returns selectedPointFrom
        coEvery { getDeliveryPointByNameUseCase(points, selectedPointTo.name) } returns selectedPointTo

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(DeliveryMainIntent.SelectAlternativeDeliveryPointFrom(selectedPointFrom.name))
        viewModel.onIntent(DeliveryMainIntent.SelectAlternativeDeliveryPointTo(selectedPointTo.name))
        advanceUntilIdle()

        val actual = viewModel.state.value.deliveryCalculatorContent?.calculateButtonEnabled
        assertEquals(false, actual)
    }

    @Test
    fun `all required fields selected EXPECT calculateButtonEnabled is true`() = runTest {
        val selectedPointFrom = points[0]
        val selectedPointTo = points[1]
        val selectedParcelType = parcelTypes[0]
        coEvery { directionRepository.getDeliveryPoints() } returns points
        coEvery { deliveryRepository.getParcelTypes() } returns parcelTypes
        every { getAlternativeDeliveryPointsUseCase(points) } returns alternativePoints
        coEvery { getDeliveryPointByNameUseCase(points, selectedPointFrom.name) } returns selectedPointFrom
        coEvery { getDeliveryPointByNameUseCase(points, selectedPointTo.name) } returns selectedPointTo

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(DeliveryMainIntent.SelectAlternativeDeliveryPointFrom(selectedPointFrom.name))
        viewModel.onIntent(DeliveryMainIntent.SelectAlternativeDeliveryPointTo(selectedPointTo.name))
        viewModel.onIntent(DeliveryMainIntent.SelectParcelType(selectedParcelType))
        advanceUntilIdle()

        val actual = viewModel.state.value.deliveryCalculatorContent?.calculateButtonEnabled
        assertEquals(true, actual)
    }
}
