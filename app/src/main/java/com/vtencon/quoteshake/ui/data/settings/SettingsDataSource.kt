package com.vtencon.quoteshake.ui.data.settings

import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {
    fun getUserName(): Flow<String>
    suspend fun getUserNameSnapshot(): String
    suspend fun setUserName(userName:String)
}