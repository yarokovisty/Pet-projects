package org.yarokovisty.delivery.feature.delivery.order.presentation.state

import delivery.feature.delivery.order.generated.resources.Res
import delivery.feature.delivery.order.generated.resources.order_address_comment_subtitle
import delivery.feature.delivery.order.generated.resources.order_address_description
import delivery.feature.delivery.order.generated.resources.order_address_subtitle
import delivery.feature.delivery.order.generated.resources.order_name_subtitle
import delivery.feature.delivery.order.generated.resources.order_phone_subtitle
import delivery.feature.delivery.order.generated.resources.order_receiver_address_title
import delivery.feature.delivery.order.generated.resources.order_receiver_title
import delivery.feature.delivery.order.generated.resources.order_sender_address_title
import delivery.feature.delivery.order.generated.resources.order_sender_title
import org.jetbrains.compose.resources.getString
import org.yarokovisty.delivery.common.delivery.order.domain.entity.ConfirmationOrder
import org.yarokovisty.delivery.common.delivery.person.domain.entity.PersonInfo
import org.yarokovisty.delivery.common.delivery.point.domain.entity.Address
import org.yarokovisty.delivery.common.delivery.presentation.StepState
import org.yarokovisty.delivery.util.phone.PhoneNumberFormatter

internal fun initial(currentStep: Int, maxSteps: Int): ConfirmationOrderState =
    ConfirmationOrderState(
        stepState = StepState(
            progress = currentStep,
            maxProgress = maxSteps
        ),
        content = null
    )

internal fun ConfirmationOrderState.setContent(
    confirmationOrder: ConfirmationOrder,
    details: List<DetailItem>
): ConfirmationOrderState =
    copy(
        content = ContentState(
            confirmationOrder = confirmationOrder,
            details = details,
            loading = false
        )
    )

internal suspend fun ConfirmationOrder.getDetails(
    phoneNumberFormatter: PhoneNumberFormatter
): List<DetailItem> =
    listOf(
        createReceiverDetail(receiver, phoneNumberFormatter),
        createSenderDetail(sender, phoneNumberFormatter),
        createSenderAddress(senderAddress),
        createReceiverAddress(receiverAddress)
    )

private suspend fun createReceiverDetail(
    receiver: PersonInfo,
    phoneNumberFormatter: PhoneNumberFormatter
): DetailItem =
    DetailItem(
        type = DetailType.RECEIVER,
        title = getString(Res.string.order_receiver_title),
        subtitle1 = getString(Res.string.order_name_subtitle),
        description1 = receiver.getFullname(),
        subtitle2 = getString(Res.string.order_phone_subtitle),
        description2 = phoneNumberFormatter.format(receiver.phone)
    )

private suspend fun createSenderDetail(
    sender: PersonInfo,
    phoneNumberFormatter: PhoneNumberFormatter
): DetailItem =
    DetailItem(
        type = DetailType.SENDER,
        title = getString(Res.string.order_sender_title),
        subtitle1 = getString(Res.string.order_name_subtitle),
        description1 = sender.getFullname(),
        subtitle2 = getString(Res.string.order_phone_subtitle),
        description2 = phoneNumberFormatter.format(sender.phone)
    )

private fun PersonInfo.getFullname(): String =
    listOfNotNull(lastname, firstname, middlename)
        .filter(String::isNotBlank)
        .joinToString(" ")

private suspend fun createSenderAddress(address: Address): DetailItem =
    DetailItem(
        type = DetailType.SENDER_ADDRESS,
        title = getString(Res.string.order_sender_address_title),
        subtitle1 = getString(Res.string.order_address_subtitle),
        description1 = address.getFullAddress(),
        subtitle2 = getString(Res.string.order_address_comment_subtitle),
        description2 = address.comment
    )

private suspend fun createReceiverAddress(address: Address): DetailItem =
    DetailItem(
        type = DetailType.RECEIVER_ADDRESS,
        title = getString(Res.string.order_receiver_address_title),
        subtitle1 = getString(Res.string.order_address_subtitle),
        description1 = address.getFullAddress(),
        subtitle2 = getString(Res.string.order_address_comment_subtitle),
        description2 = address.comment
    )

private suspend fun Address.getFullAddress(): String =
    getString(Res.string.order_address_description, street, house, apartment)

internal fun ConfirmationOrderState.loading(): ConfirmationOrderState =
    copy(
        content = content?.copy(loading = true)
    )

internal fun ConfirmationOrderState.error(): ConfirmationOrderState =
    copy(
        content = content?.copy(loading = false)
    )
