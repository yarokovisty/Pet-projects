package org.yarokovisty.delivery.common.delivery.person.data.repository

import org.yarokovisty.delivery.common.delivery.person.data.datasource.PersonLocalDataSource
import org.yarokovisty.delivery.common.delivery.person.domain.entity.PersonInfo
import org.yarokovisty.delivery.common.delivery.person.domain.repository.PersonRepository

internal class PersonRepositoryImpl(
    private val localDataSource: PersonLocalDataSource,
) : PersonRepository {

    override suspend fun getReceiver(): PersonInfo? =
        localDataSource.getReceiver()

    override suspend fun setReceiver(receiver: PersonInfo) {
        localDataSource.setReceiver(receiver)
    }

    override suspend fun getSender(): PersonInfo? =
        localDataSource.getSender()

    override suspend fun setSender(sender: PersonInfo) {
        localDataSource.setSender(sender)
    }
}
