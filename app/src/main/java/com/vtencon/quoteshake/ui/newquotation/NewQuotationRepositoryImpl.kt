package com.vtencon.quoteshake.ui.newquotation

import com.vtencon.quoteshake.ui.data.newquotation.ConnectivityChecker
import com.vtencon.quoteshake.ui.data.newquotation.NewQuotationDataSourceImpl
import com.vtencon.quoteshake.ui.data.settings.SettingsRepository
import com.vtencon.quoteshake.ui.domain.model.Quotation
import com.vtencon.quoteshake.ui.newquotation.model.toDomain
import com.vtencon.quoteshake.ui.utils.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewQuotationRepositoryImpl @Inject constructor(private val source : NewQuotationDataSourceImpl, private val connectivityChecker: ConnectivityChecker, private val settingsRepository: SettingsRepository ): NewQuotationRepository {
    override suspend fun getNewQuotation(): Result<Quotation> {
        if (!connectivityChecker.isConnectionAvailable()) {
            throw NoInternetException("No hay conexión a Internet")
        }
        return source.getQuotation(language).toDomain()
    }
    private lateinit var language: String
    init {
        CoroutineScope(SupervisorJob()).launch {
            settingsRepository.getLanguage().collect { languageCode ->
                language = languageCode.ifEmpty{ "en" }
            }
        }
    }
}