package org.yarokovisty.delivery.common.delivery.calculator.data.datasource

import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.Option
import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorage

internal class CalculatorLocalDataSource(private val storage: PreferencesStorage) {

    private companion object {

        const val OPTION_KEY = "option"
    }

    suspend fun getOption(): Option? =
        storage.getObject(OPTION_KEY, Option.serializer())

    suspend fun setOption(option: Option) {
        storage.putObject(OPTION_KEY, option, Option.serializer())
    }

    suspend fun clearOption() {
        storage.remove(OPTION_KEY)
    }
}
