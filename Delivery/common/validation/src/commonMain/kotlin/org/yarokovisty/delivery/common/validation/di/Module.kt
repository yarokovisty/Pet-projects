package org.yarokovisty.delivery.common.validation.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.yarokovisty.delivery.common.validation.validator.EmailValidator
import org.yarokovisty.delivery.common.validation.validator.PhoneValidator

val validationModule = module {
    factoryOf(::PhoneValidator)
    factoryOf(::EmailValidator)
}
