package com.vtencon.quoteshake.ui.data.settings

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(val settingsDataSource: SettingsDataSource){
    val USERNAME_KEY = stringPreferencesKey("username") // Debe coincidir con el XML
    suspend fun getUserNameSnapshot(): String = settingsDataSource.getUserNameSnapshot()
    suspend fun setUserName(userName: String) { settingsDataSource.setUserName(userName)}
}