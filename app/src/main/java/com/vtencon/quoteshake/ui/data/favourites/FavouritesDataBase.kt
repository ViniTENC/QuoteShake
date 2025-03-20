package com.vtencon.quoteshake.ui.data.favourites

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vtencon.quoteshake.ui.data.favourites.model.DataBaseQuotationDto

@Database(entities = [DataBaseQuotationDto::class], version = 1)
abstract class FavouritesDataBase : RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao
}