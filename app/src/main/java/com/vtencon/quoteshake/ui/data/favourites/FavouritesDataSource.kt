package com.vtencon.quoteshake.ui.data.favourites

import com.vtencon.quoteshake.ui.data.favourites.model.DataBaseQuotationDto
import com.vtencon.quoteshake.ui.domain.model.Quotation
import kotlinx.coroutines.flow.Flow

interface FavouritesDataSource {
    suspend fun addToDatabase(quote: DataBaseQuotationDto)
    suspend fun deleteFromDatabase(quote: DataBaseQuotationDto)
    fun getAllFavourites(): Flow<List<DataBaseQuotationDto>>
    fun getFavouriteById(id: String): Flow<Quotation?>
    fun deleteAllFavourites()
}