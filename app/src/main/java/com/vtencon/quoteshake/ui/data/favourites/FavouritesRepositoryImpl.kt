package com.vtencon.quoteshake.ui.data.favourites

import com.vtencon.quoteshake.ui.data.favourites.model.toDomain
import com.vtencon.quoteshake.ui.data.favourites.model.toDatabaseDto
import com.vtencon.quoteshake.ui.domain.model.Quotation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouritesRepositoryImpl @Inject constructor(
    private val favouritesDataSource: FavouritesDataSource
) : FavouritesRepository {

    override suspend fun addToDatabase(quote: Quotation) {
        // Convertir Quotation a DataBaseQuotationDto antes de almacenar en la base de datos
        val favouriteDto = quote.toDatabaseDto()
        favouritesDataSource.addToDatabase(favouriteDto)
    }

    override suspend fun deleteFromDatabase(quote: Quotation) {
        // Convertir Quotation a DataBaseQuotationDto antes de eliminar
        val favouriteDto = quote.toDatabaseDto()
        favouritesDataSource.deleteFromDatabase(favouriteDto)
    }

    override fun getAllFavourites(): Flow<List<Quotation>> {
        // Obtener los favoritos desde la base de datos y convertirlos de DataBaseQuotationDto a Quotation
        return favouritesDataSource.getAllFavourites()
            .map { favouriteDtos ->
                favouriteDtos.map { it.toDomain() } // Convertir cada DataBaseQuotationDto a Quotation
            }
    }

    override fun getFavouriteById(id: String): Flow<Quotation?> {
        // Obtener un favorito por su ID y convertirlo de DataBaseQuotationDto a Quotation
        return favouritesDataSource.getFavouriteById(id)
    }

    override fun deleteAllFavourites() {
        // Eliminar todos los favoritos
        favouritesDataSource.deleteAllFavourites()
    }
}
