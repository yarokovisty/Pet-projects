package org.yarokovisty.delivery.common.delivery.order.domain.usecase

import org.yarokovisty.delivery.common.delivery.calculator.domain.repository.CalculatorRepository
import org.yarokovisty.delivery.common.delivery.direction.domain.repository.DirectionRepository
import org.yarokovisty.delivery.common.delivery.parcel.domain.repository.ParcelRepository
import org.yarokovisty.delivery.common.delivery.payer.domain.repository.PayerRepository
import org.yarokovisty.delivery.common.delivery.person.domain.repository.PersonRepository
import org.yarokovisty.delivery.common.delivery.point.domain.repository.AddressRepository

class ClearConfirmationOrderUseCase(
    private val addressRepository: AddressRepository,
    private val calculatorRepository: CalculatorRepository,
    private val directionRepository: DirectionRepository,
    private val parcelRepository: ParcelRepository,
    private val payerRepository: PayerRepository,
    private val personRepository: PersonRepository
) {

    suspend operator fun invoke() = runCatching {
        addressRepository.clearReceiver()
        addressRepository.clearSender()
        calculatorRepository.clearOption()
        directionRepository.clearSelectedPointFrom()
        directionRepository.clearSelectedPointTo()
        parcelRepository.clearSelectedParcel()
        payerRepository.clearPayer()
        personRepository.clearReceiver()
        personRepository.clearSender()
    }
}
