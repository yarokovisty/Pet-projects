package org.pet.project.rickandmorty.core.dispatchers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatchersModule = module {
	single(named(AppDispatchers.IO)) { Dispatchers.IO }
	single(named(AppDispatchers.DEFAULT)) { Dispatchers.Default }
	single(named(AppDispatchers.MAIN)) { Dispatchers.Main }
	single(named(AppDispatchers.UNCONFINED)) { Dispatchers.Unconfined }
}