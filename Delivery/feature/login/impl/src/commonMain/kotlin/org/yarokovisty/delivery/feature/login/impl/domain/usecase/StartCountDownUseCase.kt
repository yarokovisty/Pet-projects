package org.yarokovisty.delivery.feature.login.impl.domain.usecase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class StartCountDownUseCase {

    private companion object {

        const val STEP_TIME_MILLS = 1000L
    }

    operator fun invoke(milliSecondsLeft: Long): Flow<Long> =
        flow {
            for (item in milliSecondsLeft downTo 0 step STEP_TIME_MILLS) {
                emit(item)
                delay(STEP_TIME_MILLS)
            }
        }
}
