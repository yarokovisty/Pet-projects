package org.yarokovisty.common.delivery.calculator.data.datasource

import org.yarokovisty.common.delivery.calculator.domain.entity.Option

internal class CalculatorLocalDataSource {

    private var option: Option? = null

    fun getOption(): Option? =
        option

    fun setOption(option: Option) {
        this.option = option
    }
}
