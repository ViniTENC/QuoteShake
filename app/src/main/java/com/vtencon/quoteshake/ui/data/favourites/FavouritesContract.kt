package com.vtencon.quoteshake.ui.data.favourites

object FavouritesContract {
    // Nombre de la base de datos
    const val DATABASE_NAME = "favourites_db"

    object FavouriteEntry {
        // Nombre de la tabla
        const val TABLE_NAME = "favourites"

        // Columnas de la tabla
        const val COLUMN_ID = "id"
        const val COLUMN_TEXT = "text"
        const val COLUMN_AUTHOR = "author"
    }
}