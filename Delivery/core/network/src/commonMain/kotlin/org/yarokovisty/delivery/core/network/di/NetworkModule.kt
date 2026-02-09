package org.yarokovisty.delivery.core.network.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.yarokovisty.delivery.core.network.client.HttpService
import org.yarokovisty.delivery.core.network.client.KtorHttpClient

val networkModule = module {
    singleOf(::KtorHttpClient)
    singleOf(::HttpService)
}
