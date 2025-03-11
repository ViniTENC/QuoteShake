package com.vtencon.quoteshake.ui.data.settings

import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {
    fun getUserName(): Flow<String>
    suspend fun getUserNameSnapshot(): String
    suspend fun setUserName(userName:String)

    // Nuevos métodos para gestionar el lenguaje
    fun getLanguage(): Flow<String>
    suspend fun getLanguageSnapshot(): String
    suspend fun setLanguage(language: String)
}