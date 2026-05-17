package org.yarokovisty.delivery.common.delivery.payer.data.datasource

import org.yarokovisty.delivery.common.delivery.payer.domain.entity.Payer
import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorage

internal class PayerLocalDataSource(private val storage: PreferencesStorage) {

    private companion object {

        const val PAYER_KEY = "payer"
    }

    suspend fun getPayer(): Payer? =
        storage.getObject(PAYER_KEY, Payer.serializer())

    suspend fun setPayer(payer: Payer) {
        storage.putObject(PAYER_KEY, payer, Payer.serializer())
    }

    suspend fun clearPayer() {
        storage.remove(PAYER_KEY)
    }
}
