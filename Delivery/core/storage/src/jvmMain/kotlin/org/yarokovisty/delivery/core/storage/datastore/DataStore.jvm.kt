package org.yarokovisty.delivery.core.storage.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import java.io.File

private fun getPreferencesDataStorePath(): String {
    val file = File(System.getProperty("java.io.tmpdir"), FILE_NAME)
    return file.absolutePath
}

internal fun createDataStore(): DataStore<Preferences> {
    val path = getPreferencesDataStorePath()
    return getPreferencesDataStore(path)
}
