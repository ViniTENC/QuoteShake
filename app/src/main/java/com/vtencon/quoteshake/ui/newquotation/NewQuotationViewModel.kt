package com.vtencon.quoteshake.ui.newquotation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vtencon.quoteshake.ui.data.favourites.FavouritesRepository
import com.vtencon.quoteshake.ui.data.settings.SettingsRepository
import com.vtencon.quoteshake.ui.domain.model.Quotation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewQuotationViewModel @Inject constructor(private val instance : NewQuotationRepository, settingsRepository: SettingsRepository, private val favouritesRepository: FavouritesRepository): ViewModel(){
    //Indica si hay algun mensaje de error a mostrar
    private val _error = MutableStateFlow<Throwable?>(null)
    val error : StateFlow<Throwable?> = _error

    fun resetError(){
        _error.value  = null
    }

    // Propiedad MutableStateFlow para la nueva cita (Quotation)
    private val _quotation = MutableStateFlow<Quotation?>(null)
    val quotation: StateFlow<Quotation?> = _quotation.asStateFlow()

    // Propiedad MutableStateFlow para indicar si se está obteniendo la nueva cita del servidor
    private val _isLoadingQuotation = MutableStateFlow(false)
    val isLoadingQuotation: StateFlow<Boolean> = _isLoadingQuotation.asStateFlow()

    fun getNewQuotation() {
        _isLoadingQuotation.value = true

        viewModelScope.launch {
            try {
                val result =
                    instance.getNewQuotation()
                result.fold(
                    onSuccess = { quotation ->
                        _quotation.value = quotation
                        setShowMessage(show = false)
                    },
                    onFailure = { error ->
                        _error.value = error
                        setShowMessage(show = true)
                        _quotation.value = null
                    }
                )
            } catch (e: Exception) {
                // Captura cualquier excepción inesperada (incluyendo NoInternetException)
                _error.value = e
                setShowMessage(show = true)
                _quotation.value = null
            } finally {
                // Asegurar que estos valores se actualicen correctamente
                _isLoadingQuotation.value = false
            }
        }
    }

    private val _showMessage = MutableStateFlow(true) // estado, solo puede modificarlo/consultarlo el ViewModel
    val showMessage = _showMessage.asStateFlow() // Fragments solo pueden acceder a esto
    fun setShowMessage(show: Boolean) { // permite cambiar el estado de la vista
        _showMessage.update { show }
    }
    val username = settingsRepository.getUserName().stateIn(scope = viewModelScope,
        initialValue = "",
        started = SharingStarted.WhileSubscribed())
    val isAddToFavouritesVisible = quotation.flatMapLatest { currentQuotation ->
        if (currentQuotation == null) flowOf(false)
        else favouritesRepository.getFavouriteById(currentQuotation.id)
            .map { quotationInDatabase ->
                quotationInDatabase == null
            }
    }.stateIn(
        scope = viewModelScope,
        initialValue = false,
        started = SharingStarted.WhileSubscribed()
    )
    fun addToFavourites() {
        viewModelScope.launch {
            try {
                favouritesRepository.addToDatabase(_quotation.value!!) // Añade la cita a la base de datos
                getNewQuotation()
            } catch (e: Exception) {
                _error.value = e // Manejo de errores
            }
        }
    }
}