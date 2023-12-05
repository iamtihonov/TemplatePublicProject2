package ua.good.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ua.good.utils.logs.logError
import java.io.IOException

/**
 * В идеале добавить уровень шифрования
 */
@Suppress("MemberVisibilityCanBePrivate")
class IntPreference(
    private val dataStore: DataStore<Preferences>,
    key: String,
    private val defaultValue: Int = 0
) {
    private val preferencesKey = intPreferencesKey(key)

    val flow: Flow<Int> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            logError(exception)
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[preferencesKey] ?: defaultValue
    }

    suspend fun get() = flow.first()

    suspend fun set(value: Int) {
        dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }
}
