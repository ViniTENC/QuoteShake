package com.vtencon.quoteshake.ui.data.favourites.model

import com.vtencon.quoteshake.ui.domain.model.Quotation

// Asumiendo que DatabaseQuotationDto y Quotation ya están definidos en algún lugar

// Función extendida para convertir DatabaseQuotationDto a Quotation
fun DataBaseQuotationDto.toDomain(): Quotation {
    return Quotation(
        id = this.id,
        text = this.text,
        author = this.author
    )
}

// Función extendida para convertir Quotation a DatabaseQuotationDto
fun Quotation.toDatabaseDto(): DataBaseQuotationDto {
    return DataBaseQuotationDto(
        id = this.id,
        text = this.text,
        author = this.author
    )
}