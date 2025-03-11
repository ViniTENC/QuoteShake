package data.favourites

import com.vtencon.quoteshake.ui.data.favourites.FavouritesDao
import com.vtencon.quoteshake.ui.data.favourites.FavouritesDataSource
import com.vtencon.quoteshake.ui.data.favourites.model.DataBaseQuotationDto
import com.vtencon.quoteshake.ui.domain.model.Quotation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouritesDataSourceImpl @Inject constructor(
    private val favouritesDao: FavouritesDao
) : FavouritesDataSource {
    override suspend fun addToDatabase(quote: DataBaseQuotationDto) {
        favouritesDao.addToDataBase(quote)
    }

    override suspend fun deleteFromDatabase(quote: DataBaseQuotationDto) {
        favouritesDao.deleteFromDataBase(quote)
    }

    override fun getAllFavourites(): Flow<List<DataBaseQuotationDto>> {
        return favouritesDao.getAllFavourites()
    }

    override fun getFavouriteById(id: String): Flow<Quotation?> {
        return favouritesDao.getFavouriteById(id)
    }

    override fun deleteAllFavourites() {
        favouritesDao.deleteAllFavourites()
    }
}
