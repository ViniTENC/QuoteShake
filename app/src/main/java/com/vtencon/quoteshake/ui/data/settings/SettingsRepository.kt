package com.vtencon.quoteshake.ui.data.settings

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getUserName() : Flow<String>
    suspend fun getUserNameSnapshot(): String
    suspend fun setUserName(userName: String)
    fun saveSetting(string1: String, string2: String)
    fun getSetting(string: String): String?

}