package com.vtencon.quoteshake.ui.newquotation

import android.R
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vtencon.quoteshake.ui.data.settings.SettingsRepository
import com.vtencon.quoteshake.ui.domain.model.Quotation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewQuotationViewModel @Inject constructor(private val instance : NewQuotationRepository, private val settingsRepository: SettingsRepository): ViewModel(){
    //Indica si hay algun mensaje de error a mostrar
    private val _error = MutableStateFlow<Throwable?>(null)
    val error : StateFlow<Throwable?> = _error

    fun resetError(){
        _error.value  = null
    }

    val username: StateFlow<String> = settingsRepository.getUserName().stateIn(
        scope = viewModelScope,
        initialValue = "",
        started = SharingStarted.WhileSubscribed()
    )

    // Método privado que genera un nombre aleatorio
    private fun getUserName(): String {
        return setOf("Alice", "Bob", "Charlie", "David", "Emma", "").random()
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
                val result = instance.getNewQuotation() // Asegurar que devuelve un Result
                result.fold(
                    onSuccess = { quotation ->
                        _quotation.value = quotation
                        setShowMessage(show = false)
                        _boton.value = true
                    },
                    onFailure = { error ->
                        _error.value = error
                        setShowMessage(show = true)
                        _quotation.value = null
                        _boton.value = false
                    }
                )
            } catch (e: Exception) {
                // Captura cualquier excepción inesperada (incluyendo NoInternetException)
                _error.value = e
                setShowMessage(show = true)
                _quotation.value = null
                _boton.value = false
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

    private val _boton = MutableStateFlow<Boolean>(false)
    val boton : StateFlow<Boolean> = _boton.asStateFlow()

    fun addToFavourites() {
        _boton.value = false
    }
}