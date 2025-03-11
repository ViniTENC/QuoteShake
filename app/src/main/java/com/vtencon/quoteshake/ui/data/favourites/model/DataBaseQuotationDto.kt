package com.vtencon.quoteshake.ui.data.favourites.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vtencon.quoteshake.ui.data.favourites.FavouritesContract.FavouriteEntry
import com.vtencon.quoteshake.ui.data.favourites.FavouritesContract.FavouriteEntry.TABLE_NAME

@Entity (tableName =
TABLE_NAME)
data class DataBaseQuotationDto( @PrimaryKey
                                 @ColumnInfo(name = FavouriteEntry.COLUMN_ID)
                                 val id: String,

                                 @ColumnInfo(name = FavouriteEntry.COLUMN_TEXT)
                                 val text: String,

                                 @ColumnInfo(name = FavouriteEntry.COLUMN_AUTHOR)
                                 val author: String)