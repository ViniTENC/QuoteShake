package com.vtencon.quoteshake.ui.newquotation

import com.vtencon.quoteshake.ui.domain.model.Quotation

interface NewQuotationRepository {
    suspend fun getNewQuotation():
            Result<Quotation>
}