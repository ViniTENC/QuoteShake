package com.vtencon.quoteshake.ui.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class SettingsDataSourceImpl @Inject constructor(private val dataStore : DataStore<Preferences>) : SettingsDataSource{
    val usernameKey = stringPreferencesKey("username") // Debe coincidir con el XML
    private val languageKey = stringPreferencesKey("language") // Nueva constante para el lenguaje

    override fun getUserName(): Flow<String> {
        return getStringPreference(usernameKey)
    }

    override suspend fun getUserNameSnapshot(): String = getStringPreferenceSnapshot(usernameKey)

    override suspend fun setUserName(userName: String) {
        setStringPreference(usernameKey, userName)
    }


    override fun getLanguage(): Flow<String> {
        return getStringPreference(languageKey, "en") // El valor por defecto es "en"
    }

    override suspend fun getLanguageSnapshot(): String = getStringPreferenceSnapshot(languageKey, "en")


    override suspend fun setLanguage(language: String) {
        setStringPreference(languageKey, language)
    }
    // Métodos privados para reutilizar la lógica de manejo de preferencias
    private fun getStringPreference(key: Preferences.Key<String>, defaultValue: String = ""): Flow<String> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else throw exception
        }.map { preferences ->
            preferences[key].orEmpty()
        }
    }

    private suspend fun getStringPreferenceSnapshot(key: Preferences.Key<String>, defaultValue: String = ""): String {
        return dataStore.data.first()[key] ?: defaultValue
    }

    private suspend fun setStringPreference(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}