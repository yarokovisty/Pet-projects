package org.yarokovisty.delivery.di.module

import org.koin.dsl.module
import org.yarokovisty.delivery.common.validation.di.validationModule
import org.yarokovisty.delivery.user.di.userModule

val commonModule = module {
    includes(
        userModule,
        validationModule
    )
}
