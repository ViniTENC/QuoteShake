package com.vtencon.quoteshake.ui.newquotation

import com.vtencon.quoteshake.ui.data.newquotation.ConnectivityChecker
import com.vtencon.quoteshake.ui.data.newquotation.NewQuotationDataSourceImpl
import com.vtencon.quoteshake.ui.domain.model.Quotation
import com.vtencon.quoteshake.ui.newquotation.model.toDomain
import com.vtencon.quoteshake.ui.utils.NoInternetException
import javax.inject.Inject

class NewQuotationRepositoryImpl @Inject constructor(private val source : NewQuotationDataSourceImpl, private val connectivityChecker: ConnectivityChecker ): NewQuotationRepository {
    override suspend fun getNewQuotation(): Result<Quotation> {
        if (!connectivityChecker.isConnectionAvailable()) {
            throw NoInternetException("No hay conexión a Internet")
        }
        val language = arrayOf("en", "ru", "xx").random()
        return source.getQuotation(language).toDomain()
    }
}