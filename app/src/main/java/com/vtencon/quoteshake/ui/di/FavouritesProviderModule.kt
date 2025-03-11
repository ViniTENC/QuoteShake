package com.vtencon.quoteshake.ui.di

import android.content.Context
import androidx.room.Room
import com.vtencon.quoteshake.ui.data.favourites.FavouritesContract
import com.vtencon.quoteshake.ui.data.favourites.FavouritesDao
import com.vtencon.quoteshake.ui.data.favourites.FavouritesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FavouritesProviderModule {
    @Provides
    @Singleton
    fun provideFavouritesDatabase(@ApplicationContext context: Context): FavouritesDataBase {
        return Room.databaseBuilder(
            context,
            FavouritesDataBase::class.java,
            FavouritesContract.DATABASE_NAME // Usamos el nombre de la base de datos definido en FavouritesContract
        ).build()
    }
    @Provides
    @Singleton
    fun provideFavouritesDao(favouritesDatabase: FavouritesDataBase): FavouritesDao {
        return favouritesDatabase.favouritesDao() // Devolvemos la instancia de FavouritesDao desde la base de datos
    }}