package org.yarokovisty.delivery.common.delivery.person.domain.repository

import org.yarokovisty.delivery.common.delivery.person.domain.entity.PersonInfo

interface PersonRepository {

    suspend fun getReceiver(): PersonInfo?

    suspend fun setReceiver(receiver: PersonInfo)

    suspend fun clearReceiver()

    suspend fun getSender(): PersonInfo?

    suspend fun setSender(sender: PersonInfo)

    suspend fun clearSender()
}
