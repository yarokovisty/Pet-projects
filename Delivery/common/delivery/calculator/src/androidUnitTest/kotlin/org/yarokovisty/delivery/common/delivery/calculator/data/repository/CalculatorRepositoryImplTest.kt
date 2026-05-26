package org.yarokovisty.delivery.common.delivery.calculator.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.delivery.calculator.data.datasource.CalculatorLocalDataSource
import org.yarokovisty.delivery.common.delivery.calculator.data.datasource.CalculatorRemoteDataSource
import org.yarokovisty.delivery.common.delivery.calculator.data.model.OptionListResponse
import org.yarokovisty.delivery.common.delivery.calculator.data.model.OptionResponse
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.Option
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.OptionType
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.PackageType
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CalculatorRepositoryImplTest {

    private val remoteDataSource: CalculatorRemoteDataSource = mockk()
    private val localDataSource: CalculatorLocalDataSource = mockk(relaxUnitFun = true)
    private val repository = CalculatorRepositoryImpl(remoteDataSource, localDataSource)

    private val parcelInfo = ParcelInfo(
        id = "envelope",
        type = PackageType.ENVELOPE,
        name = "Envelope",
        length = 10,
        width = 5,
        height = 2,
        weight = 1
    )

    private val senderPoint = DeliveryPoint(
        id = "sender-1",
        name = "Sender",
        latitude = 55.0,
        longitude = 37.0
    )

    private val receiverPoint = DeliveryPoint(
        id = "receiver-1",
        name = "Receiver",
        latitude = 59.0,
        longitude = 30.0
    )

    private val optionListResponse = OptionListResponse(
        options = listOf(
            OptionResponse(
                id = "opt-1",
                days = 3,
                price = 50000,
                name = "Default",
                type = "default"
            ),
            OptionResponse(
                id = "opt-2",
                days = 1,
                price = 100000,
                name = "Express",
                type = "express"
            )
        )
    )

    @Test
    fun `get option list EXPECT mapped options`() = runTest {
        val expected = listOf(
            Option(id = "opt-1", price = 500.0, days = 3, type = OptionType.DEFAULT),
            Option(id = "opt-2", price = 1000.0, days = 1, type = OptionType.EXPRESS)
        )
        coEvery { remoteDataSource.getOptionList(any()) } returns optionListResponse

        val actual = repository.getOptionList(parcelInfo, senderPoint, receiverPoint)

        assertEquals(expected, actual)
    }

    @Test
    fun `get option list EXPECT invoke remote data source`() = runTest {
        coEvery { remoteDataSource.getOptionList(any()) } returns optionListResponse

        repository.getOptionList(parcelInfo, senderPoint, receiverPoint)

        coVerify { remoteDataSource.getOptionList(any()) }
    }

    @Test
    fun `get option when option is set EXPECT option`() = runTest {
        val expected = Option(id = "opt-1", price = 500.0, days = 3, type = OptionType.DEFAULT)
        coEvery { localDataSource.getOption() } returns expected

        val actual = repository.getOption()

        assertEquals(expected, actual)
    }

    @Test
    fun `get option when option is not set EXPECT null`() = runTest {
        coEvery { localDataSource.getOption() } returns null

        val actual = repository.getOption()

        assertNull(actual)
    }

    @Test
    fun `set option EXPECT invoke local data source`() = runTest {
        val option = Option(id = "opt-1", price = 500.0, days = 3, type = OptionType.DEFAULT)

        repository.setOption(option)

        coVerify { localDataSource.setOption(option) }
    }
}
