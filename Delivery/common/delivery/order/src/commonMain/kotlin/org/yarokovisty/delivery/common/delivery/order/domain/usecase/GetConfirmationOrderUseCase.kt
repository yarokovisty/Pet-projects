package org.yarokovisty.delivery.common.delivery.order.domain.usecase

import org.yarokovisty.delivery.common.delivery.calculator.domain.repository.CalculatorRepository
import org.yarokovisty.delivery.common.delivery.direction.domain.repository.DirectionRepository
import org.yarokovisty.delivery.common.delivery.order.domain.entity.ConfirmationOrder
import org.yarokovisty.delivery.common.delivery.parcel.domain.repository.ParcelRepository
import org.yarokovisty.delivery.common.delivery.payer.domain.repository.PayerRepository
import org.yarokovisty.delivery.common.delivery.person.domain.repository.PersonRepository
import org.yarokovisty.delivery.common.delivery.point.domain.repository.AddressRepository

class GetConfirmationOrderUseCase(
    private val calculatorRepository: CalculatorRepository,
    private val directionRepository: DirectionRepository,
    private val parcelRepository: ParcelRepository,
    private val personRepository: PersonRepository,
    private val addressRepository: AddressRepository,
    private val payerRepository: PayerRepository
) {

    suspend operator fun invoke(): ConfirmationOrder =
        ConfirmationOrder(
            packageId = parcelRepository.getSelectedParcel()?.id ?: error("Package ID can't be null"),
            option = calculatorRepository.getOption() ?: error("Option can't be null"),
            senderPointId = directionRepository.getSelectedPointFrom()?.id ?: error("Sender point id can't be null"),
            sender = personRepository.getSender() ?: error("Sender can't be null"),
            senderAddress = addressRepository.getSender() ?: error("Sender address can't be null"),
            receiverPointId = directionRepository.getSelectedPointTo()?.id ?: error("Receiver point id can't be null"),
            receiver = personRepository.getReceiver() ?: error("Receiver can't be null"),
            receiverAddress = addressRepository.getReceiver() ?: error("Receiver address can't be null"),
            payer = payerRepository.getPayer() ?: error("Payer can't be null"),
        )
}
