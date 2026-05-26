package org.yarokovisty.delivery.common.delivery.person.data.mapper

import org.yarokovisty.delivery.common.delivery.person.data.model.PersonInfoRequest
import org.yarokovisty.delivery.common.delivery.person.data.model.PersonInfoResponse
import org.yarokovisty.delivery.common.delivery.person.domain.entity.PersonInfo

fun PersonInfoResponse.toItem(): PersonInfo =
    PersonInfo(
        firstname = firstname,
        lastname = lastname,
        middlename = middlename?.ifEmpty { null },
        phone = phone
    )

fun PersonInfo.toRequest(): PersonInfoRequest =
    PersonInfoRequest(
        firstname = firstname,
        lastname = lastname,
        middlename = middlename ?: "",
        phone = phone
    )
