package com.vtencon.quoteshake.ui.di

import com.vtencon.quoteshake.ui.data.favourites.FavouritesDataSource
import com.vtencon.quoteshake.ui.data.favourites.FavouritesRepository
import com.vtencon.quoteshake.ui.data.favourites.FavouritesRepositoryImpl
import com.vtencon.quoteshake.ui.data.settings.SettingsRepository
import com.vtencon.quoteshake.ui.data.settings.SettingsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.favourites.FavouritesDataSourceImpl
import javax.inject.Singleton

@Module  @InstallIn(SingletonComponent::class)
abstract class FavouritesBinderModule {
    // Función para vincular la implementación de FavouritesDataSource a su interfaz
    @Binds
    @Singleton
    abstract fun bindFavouritesDataSource(
        favouritesDataSourceImpl: FavouritesDataSourceImpl
    ): FavouritesDataSource

    // Función para vincular la implementación de FavouritesRepository a su interfaz
    @Binds
    @Singleton
    abstract fun bindFavouritesRepository(
        favouritesRepositoryImpl: FavouritesRepositoryImpl
    ): FavouritesRepository}