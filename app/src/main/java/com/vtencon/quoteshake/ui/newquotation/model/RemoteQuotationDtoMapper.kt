package com.vtencon.quoteshake.ui.newquotation.model

import com.vtencon.quoteshake.ui.domain.model.Quotation
import retrofit2.Response
import java.io.IOException

fun RemoteQuotationDto.toDomain() = Quotation(id = quoteLink, text = quoteText, author
= quoteAuthor)
fun Response<RemoteQuotationDto>.toDomain() =
    if (isSuccessful) Result.success((body() as RemoteQuotationDto).toDomain())
    else Result.failure(IOException())