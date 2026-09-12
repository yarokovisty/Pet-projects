package org.yarokovisty.shift_pizza.core.network.di

import org.koin.core.annotation.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NetworkClient(val value: Type)

enum class Type {
    DEFAULT
}
