package com.vtencon.quoteshake.ui.data.settings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(val settingsDataSource: SettingsDataSource) : SettingsRepository{
    override fun getUserName(): Flow<String> {
        return settingsDataSource.getUserName()
    }

    override suspend fun getUserNameSnapshot(): String = settingsDataSource.getUserNameSnapshot()
    override suspend fun setUserName(userName: String) { settingsDataSource.setUserName(userName)}
    override fun getLanguage(): Flow<String> {
        return settingsDataSource.getLanguage()}

    override suspend fun getLanguageSnapshot(): String = settingsDataSource.getLanguageSnapshot()

    override suspend fun setLanguage(language: String) {
        settingsDataSource.setLanguage(language)
    }

}