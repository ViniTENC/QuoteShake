package com.vtencon.quoteshake.ui.data.newquotation

import com.vtencon.quoteshake.ui.domain.model.Quotation
import com.vtencon.quoteshake.ui.newquotation.model.RemoteQuotationDto
import retrofit2.Response

interface NewQuotationDataSource {
    suspend fun getQuotation(language : String):
            Response<RemoteQuotationDto>
}