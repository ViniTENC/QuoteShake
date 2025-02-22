package com.vtencon.quoteshake.ui.newquotation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import com.vtencon.quoteshake.ui.domain.model.Quotation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewQuotationViewModel : ViewModel(){
    // Propiedad para el nombre de usuario
    private val _username = MutableStateFlow(getUserName())
    val username: StateFlow<String> = _username.asStateFlow()

    // Método privado que genera un nombre aleatorio
    private fun getUserName(): String {
        return setOf("Alice", "Bob", "Charlie", "David", "Emma", "").random()
    }

    // Propiedad MutableStateFlow para la nueva cita (Quotation)
    private val _quotation = MutableStateFlow<String?>(null)
    val quotation: StateFlow<String?> = _quotation.asStateFlow()

    // Propiedad MutableStateFlow para indicar si se está obteniendo la nueva cita del servidor
    private val _isLoadingQuotation = MutableStateFlow(false)
    val isLoadingQuotation: StateFlow<Boolean> = _isLoadingQuotation.asStateFlow()

    fun getNewQuotation() {
        // Mostrar el ícono de carga
        _isLoadingQuotation.value = true
        val num = (0..99).random()
        _quotation.update {
            Quotation(
                "$num",
                "Quotation text #$num",
                "Author #$num"
            ).toString()
        }
        _isLoadingQuotation.value = false
    }
    private val _showMessage = MutableStateFlow(true) // estado, solo puede modificarlo/consultarlo el ViewModel
    val showMessage = _showMessage.asStateFlow() // Fragments solo pueden acceder a esto
    fun setShowMessage(show: Boolean) { // permite cambiar el estado de la vista
        _showMessage.update { show }
    }
}