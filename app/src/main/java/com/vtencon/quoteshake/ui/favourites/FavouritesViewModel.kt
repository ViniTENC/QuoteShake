package com.vtencon.quoteshake.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vtencon.quoteshake.ui.domain.model.Quotation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(): ViewModel(){
    private val _list = MutableStateFlow<List<Quotation>>(getFavoriteQuotations())
    val list = _list.asStateFlow()
    val favoriteQuotations: StateFlow<List<Quotation>> = _list

    // Propiedad MutableStateFlow para la nueva cita (Quotation)
    private val _quotation = MutableStateFlow<Quotation?>(null)
    val quotation: StateFlow<Quotation?> = _quotation.asStateFlow()

    val isDeleteAllMenuVisible = favoriteQuotations.map { list ->
        list.isNotEmpty()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = true
    )
    private fun getFavoriteQuotations(): List<Quotation>{
        val list = mutableListOf<Quotation>()
        for (i in 0..20){
            val num = (0..99).random()
            list.add(Quotation(
                "$num",
                "Quotation text #$num",
                "Albert Einstein"
            ))
        }
        return list
    }
    fun deleteAllQuotations(){
        _list.value = emptyList()
    }
    fun deleteQuotationAtPosition(position: Int) {
        val copia = _list.value.toList() // Copia inmutable de la lista actual
        if (position in copia.indices) { // Verifica que la posición es válida
            _list.value = copia - copia[position] // Elimina el elemento y asigna la nueva lista
        }
    }
}