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
import kotlinx.coroutines.flow.stateIn
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

    fun deleteAllQuotations(){
        viewModelScope.launch(Dispatchers.IO){favouritesRepository.deleteAllFavourites()}   }
    fun deleteQuotationAtPosition(position: Int) {
        viewModelScope.launch{favouritesRepository.deleteFromDatabase(list.value.getOrNull(position)!!)}
    }
}