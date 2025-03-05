package com.vtencon.quoteshake.ui.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.preference.PreferenceDataStore
import com.vtencon.quoteshake.ui.data.settings.SettingsPreferenceDataStore
import com.vtencon.quoteshake.ui.data.settings.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module @InstallIn(SingletonComponent::class)
abstract class SettingsProviderModule {
    private val PREFERENCE_FILE_NAME = "settings_preferences"
    @Provides
    @Singleton
    fun provideSettingsPreferenceDataStore(settingsRepository: SettingsRepository): PreferenceDataStore {
        return SettingsPreferenceDataStore(settingsRepository)
    }
    @Provides
    @Singleton
    fun provideSettingsDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
            migrations = listOf(),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile(PREFERENCE_FILE_NAME) }
        )
    }}