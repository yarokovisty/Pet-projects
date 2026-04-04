package org.yarokovisty.delivery.di.module

import org.koin.dsl.module
import org.yarokovisty.delivery.common.auth.di.authModule
import org.yarokovisty.delivery.common.validation.di.validationModule

val commonModule = module {
    includes(
        authModule,
        validationModule
    )
}
