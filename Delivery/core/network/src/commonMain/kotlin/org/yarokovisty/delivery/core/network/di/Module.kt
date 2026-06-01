package org.yarokovisty.delivery.core.network.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.core.network.client.KtorAuthHttpClient
import org.yarokovisty.delivery.core.network.client.KtorDefaultHttpClient
import org.yarokovisty.delivery.core.network.token.TokenProvider
import org.yarokovisty.delivery.core.network.token.TokenProviderImpl

private enum class KtorHttpClientType {
    DEFAULT,
    AUTH
}

val defaultHttpClientQualifier = named(KtorHttpClientType.DEFAULT)
val authHttpClientQualifier = named(KtorHttpClientType.AUTH)

val networkModule = module {
    singleOf(::TokenProviderImpl) bind TokenProvider::class
    single(defaultHttpClientQualifier) { KtorDefaultHttpClient(json = get()) }
    single(authHttpClientQualifier) { KtorAuthHttpClient(json = get(), tokenProvider = get()) }
}
