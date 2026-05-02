package org.yarokovisty.delivery.feature.delivery.person.presentation.converter

import org.yarokovisty.delivery.common.delivery.person.domain.entity.PersonInfo
import org.yarokovisty.delivery.common.profile.main.domain.entity.User

internal fun User.toPersonInfo(): PersonInfo =
    PersonInfo(
        firstname = this.firstname ?: "",
        lastname = this.lastname ?: "",
        middlename = this.middlename,
        phone = this.phone
    )
