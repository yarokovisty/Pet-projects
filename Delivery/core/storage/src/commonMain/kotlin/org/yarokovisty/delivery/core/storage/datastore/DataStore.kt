package org.yarokovisty.delivery.core.storage.datastore

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import okio.Path.Companion.toPath

internal const val FILE_NAME = "delivery.preferences_pb"

internal fun getPreferencesDataStore(path: String) = PreferenceDataStoreFactory.createWithPath {
    path.toPath()
}
