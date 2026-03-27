package org.yarokovisty.delivery.core.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

private fun getPreferencesDataStorePath(appContext: Context): String =
    appContext.filesDir.resolve(FILE_NAME).absolutePath

internal fun createDataStore(context: Context): DataStore<Preferences> {
    val path = getPreferencesDataStorePath(context)
    return getPreferencesDataStore(path)
}
