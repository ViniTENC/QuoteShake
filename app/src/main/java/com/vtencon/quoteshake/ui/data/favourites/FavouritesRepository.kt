package com.vtencon.quoteshake.ui.data.favourites
import com.vtencon.quoteshake.ui.domain.model.Quotation
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    suspend fun addToDatabase(quote: Quotation)
    suspend fun deleteFromDatabase(quote: Quotation)
    fun getAllFavourites(): Flow<List<Quotation>>
    fun getFavouriteById(id: String): Flow<Quotation?>
    fun deleteAllFavourites()
}