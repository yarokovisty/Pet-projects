package org.yarokovisty.delivery.common.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coroutinesModule = module {
    single(named(DispatchersQualifier.IO)) { Dispatchers.IO }
    single(named(DispatchersQualifier.DEFAULT)) { Dispatchers.Default }
    single(named(DispatchersQualifier.MAIN)) { Dispatchers.Main }
    single(named(DispatchersQualifier.UNCONFINED)) { Dispatchers.Unconfined }
}
