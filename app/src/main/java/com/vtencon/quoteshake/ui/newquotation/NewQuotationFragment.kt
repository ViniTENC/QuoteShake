package com.vtencon.quoteshake.ui.newquotation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.vtencon.quoteshake.R
import com.vtencon.quoteshake.databinding.FragmentNewQuotationBinding
import com.vtencon.quoteshake.ui.utils.NoInternetException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class NewQuotationFragment : Fragment(R.layout.fragment_new_quotation), MenuProvider {
    private val viewModel: NewQuotationViewModel by viewModels()
    private var _binding: FragmentNewQuotationBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewQuotationBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "QuoteShake"
        requireActivity().addMenuProvider(this,
            viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Observa el estado de showMessage para actualizar la visibilidad de tvGreeting
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showMessage.collect { show ->
                    binding.tvGreeting.isVisible = show
                    //binding.tvGreeting.text = getString(R.string.welcomeMessage, viewModel.username.value.ifEmpty { "Guest" })
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.username.collect { show ->
                    //binding.tvGreeting.isVisible = show
                    binding.tvGreeting.text = getString(R.string.welcomeMessage, viewModel.username.value.ifEmpty { "Guest" })
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
        // Asociar el SwipeRefreshLayout con el ViewModel para iniciar la carga de una nueva cita
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.getNewQuotation() // Llamar al método del ViewModel al refrescar
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.quotation.collect { quotation ->
                    binding.tvQuotationText.text = quotation?.text
                    binding.tvQuotationAuthor.text = if (quotation?.author?.isEmpty() == true) "Anonymous" else quotation?.author
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isAddToFavouritesVisible.collect { isVisible ->
                    binding.fabFavourite.isVisible = isVisible
                    if (!isVisible) {
                    viewModel.getNewQuotation() } // aca esta lo mismo
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect { error ->
                    error?.let {
                        val message = if (it is NoInternetException) {
                            "No hay conexión a Internet. Verifica tu conexión e intenta de nuevo."
                        } else {
                            "Ocurrió un error al obtener la cotización."
                        }

                        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
                        viewModel.resetError()
                    }
                }
            }
        }
        binding.fabFavourite.setOnClickListener {
            viewModel.addToFavourites()
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

