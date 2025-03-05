package com.vtencon.quoteshake.ui.di

import com.vtencon.quoteshake.ui.data.settings.SettingsDataSource
import com.vtencon.quoteshake.ui.data.settings.SettingsDataSourceImpl
import com.vtencon.quoteshake.ui.data.settings.SettingsRepository
import com.vtencon.quoteshake.ui.data.settings.SettingsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module @InstallIn(SingletonComponent::class)
abstract class SettingsBinderModule {

    @Binds
    abstract fun bindSettingsDataSource(dataSourceImpl: SettingsDataSourceImpl): SettingsDataSource

    @Binds
    abstract fun bindSettingsRepository(repositoryImpl: SettingsRepositoryImpl): SettingsRepository
}