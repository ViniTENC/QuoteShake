package com.vtencon.quoteshake.ui.data.favourites

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vtencon.quoteshake.ui.data.favourites.FavouritesContract.FavouriteEntry
import com.vtencon.quoteshake.ui.data.favourites.FavouritesContract.FavouriteEntry.TABLE_NAME
import com.vtencon.quoteshake.ui.data.favourites.model.DataBaseQuotationDto
import com.vtencon.quoteshake.ui.domain.model.Quotation
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToDataBase( quote : DataBaseQuotationDto)
    @Delete
    suspend fun deleteFromDataBase(quote : DataBaseQuotationDto)
    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllFavourites(): Flow<List<DataBaseQuotationDto>>
    @Query("SELECT * FROM $TABLE_NAME WHERE ${FavouriteEntry.COLUMN_ID} = :id")
    fun getFavouriteById(id: String): Flow<Quotation?>

    @Query("DELETE FROM $TABLE_NAME")
    fun deleteAllFavourites()
}