package com.vtencon.quoteshake.ui.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SettingsDataSourceImpl @Inject constructor(private val dataStore : DataStore<Preferences>) {
    val USERNAME_KEY = stringPreferencesKey("username") // Debe coincidir con el XML
    suspend fun getUserNameSnapshot(): String = dataStore.data.first()[USERNAME_KEY] ?:""
    suspend fun setUserName(userName: String) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = userName
        }
    }
}