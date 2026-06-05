package org.yarokovisty.delivery.navigation.router

import io.mockk.mockk
import io.mockk.verify
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DirectionType
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.PackageType
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.feature.delivery.calculator.navigation.CalculatorDestination
import org.yarokovisty.delivery.feature.delivery.direction.navigation.DirectionDestination
import org.yarokovisty.delivery.feature.history.details.navigation.OrderDetailsDestination
import org.yarokovisty.delivery.libs.navigation.backstack.GlobalBackStack
import kotlin.test.Test

class DeliveryRouterImplTest {

    private val globalBackStack: GlobalBackStack = mockk(relaxed = true)
    private val router = DeliveryRouterImpl(globalBackStack)

    // region openDirectionScreen

    @Test
    fun `open direction screen with FROM type EXPECT push DirectionDestination FROM to global back stack`() {
        router.openDirectionScreen(DirectionType.FROM)

        verify { globalBackStack.push(DirectionDestination(DirectionType.FROM)) }
    }

    @Test
    fun `open direction screen with TO type EXPECT push DirectionDestination TO to global back stack`() {
        router.openDirectionScreen(DirectionType.TO)

        verify { globalBackStack.push(DirectionDestination(DirectionType.TO)) }
    }

    // endregion

    // region openCalculatorScreen

    @Test
    fun `open calculator screen EXPECT push CalculatorDestination to global back stack`() {
        val parcelInfo = ParcelInfo(
            id = "parcel-1",
            type = PackageType.BOX_M,
            name = "Documents",
            length = 30,
            width = 20,
            height = 10,
            weight = 2
        )
        val senderPoint = DeliveryPoint(
            id = "point-sender-1",
            name = "Moscow, Red Square 1",
            latitude = 55.7539,
            longitude = 37.6208
        )
        val receiverPoint = DeliveryPoint(
            id = "point-receiver-1",
            name = "Saint Petersburg, Nevsky Prospect 1",
            latitude = 59.9343,
            longitude = 30.3351
        )

        router.openCalculatorScreen(parcelInfo, senderPoint, receiverPoint)

        verify { globalBackStack.push(CalculatorDestination(parcelInfo, senderPoint, receiverPoint)) }
    }

    // endregion

    // region openOrderDetailsScreen

    @Test
    fun `open order details screen EXPECT push OrderDetailsDestination to global back stack`() {
        val orderId = "order-123"

        router.openOrderDetailsScreen(orderId)

        verify { globalBackStack.push(OrderDetailsDestination(orderId)) }
    }

    // endregion
}
