package com.vtencon.quoteshake.ui.newquotation

import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vtencon.quoteshake.R
import com.vtencon.quoteshake.databinding.FragmentNewQuotationBinding
import kotlinx.coroutines.launch

class NewQuotationFragment : Fragment(R.layout.fragment_new_quotation), MenuProvider {
    private val viewModel: NewQuotationViewModel by viewModels()
    private var _binding: FragmentNewQuotationBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewQuotationBinding.bind(view)

        // Observa el estado de showMessage para actualizar la visibilidad de tvGreeting
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showMessage.collect { show ->
                    binding.tvGreeting.isVisible = show
                }
            }
        }

        // Observa isLoadingQuotation para mostrar/ocultar el icono de refresco
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoadingQuotation.collect { isLoading ->
                    binding.swipeToRefresh.isRefreshing = isLoading
                }
            }
        }

        // Observa la nueva cita y actualiza el texto y el autor
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.quotation.collect { quotation ->
                    quotation?.let {
                        val parts = it.split(",") // Suponiendo que la cita y el autor están separados por coma
                        val quotationText = parts.getOrNull(1) ?: ""
                        val authorText = parts.getOrNull(2)?.trim() ?: "Anonymous"
                        binding.tvQuotationText.text = quotationText
                        binding.tvQuotationAuthor.text = if (authorText.isEmpty()) "Anonymous" else authorText
                    }
                }
            }
        }
        // Asociar el SwipeRefreshLayout con el ViewModel para iniciar la carga de una nueva cita
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.getNewQuotation() // Llamar al método del ViewModel al refrescar
            Log.d("NewQuotationFragment", "Se ha activado el refresco")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_new_quotation, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        requireActivity().addMenuProvider(this,
        viewLifecycleOwner, Lifecycle.State.RESUMED)
        return when (menuItem.itemId) {
            R.id.refreshManual-> {
                // Llamamos a getNewQuotation() si el ítem seleccionado es el correcto
                viewModel.getNewQuotation()
                true  // Indica que hemos manejado este ítem del menú
            }
            else -> false  // No hemos manejado este ítem, permite que otros lo manejen si es necesario
        }
    }
}

