package org.yarokovisty.delivery.common.delivery.step

import org.koin.core.qualifier.named
import org.koin.dsl.module

const val MAX_STEPS = 7
object MaxStepQualifier

val deliveryStepModule = module {
    single(named<MaxStepQualifier>()) { MAX_STEPS }
}
