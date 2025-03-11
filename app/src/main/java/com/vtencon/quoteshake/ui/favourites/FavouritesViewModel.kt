package com.vtencon.quoteshake.ui.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vtencon.quoteshake.ui.data.favourites.FavouritesRepository
import com.vtencon.quoteshake.ui.domain.model.Quotation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val favouritesRepository: FavouritesRepository): ViewModel(){
    val list = favouritesRepository.getAllFavourites().stateIn(
        scope = viewModelScope,
        initialValue = listOf(),
        started = SharingStarted.WhileSubscribed()
    )
    val favoriteQuotations: StateFlow<List<Quotation>> = list

    // Propiedad MutableStateFlow para la nueva cita (Quotation)
    private val _quotation = MutableStateFlow<Quotation?>(null)
    val quotation: StateFlow<Quotation?> = _quotation.asStateFlow()

    //val isDeleteAllMenuVisible = favoriteQuotations.map { list ->
    //    list.isNotEmpty()
    //}.stateIn(
    //    scope = viewModelScope,
    //    started = SharingStarted.WhileSubscribed(),
    //    initialValue = true
    //)
    fun deleteAllQuotations(){
        viewModelScope.launch(Dispatchers.IO){favouritesRepository.deleteAllFavourites()}   }
    fun deleteQuotationAtPosition(position: Int) {
        //val copia = _list.value.toList() // Copia inmutable de la lista actual
        //if (position in copia.indices) { // Verifica que la posición es válida
        //    _list.value = copia - copia[position] // Elimina el elemento y asigna la nueva lista
        //}
        viewModelScope.launch{favouritesRepository.deleteFromDatabase(list.value.getOrNull(position)!!)}
    }
}