package com.vtencon.quoteshake.ui.favourites
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vtencon.quoteshake.R
import com.vtencon.quoteshake.databinding.FragmentFavouritesBinding
import dagger.hilt.android.AndroidEntryPoint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.getValue
@AndroidEntryPoint
class FavouriteFragment : Fragment(R.layout.fragment_favourites) , MenuProvider {
    private val viewModel: FavouritesViewModel by activityViewModels()
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavouritesBinding.bind(view)

        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.favourite)

        // Definir el comportamiento al hacer clic en un autor
        val onItemClick: (String) -> Unit = { authorName ->
            if (authorName.equals(getString(R.string.anonymous), ignoreCase = true)) {
                Snackbar.make(
                    binding.root,
                    getString(R.string.anonymousWarning),
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                val searchUrl = "https://en.wikipedia.org/wiki/Special:Search?search=$authorName"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl))
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.actionNotSuport),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
        // Inicializar el adaptador
        val adapter = QuotationListAdapter(onItemClick)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        requireActivity().addMenuProvider(this,
            viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Observa el estado de favoriteQuotations
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteQuotations.collect { quotations ->
                    adapter.submitList(quotations)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteQuotations.collect {
                    withContext(Dispatchers.Main) {
                        requireActivity().invalidateMenu()
                    }                }
            }
        }
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_favourites,
            menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId){
            R.id.deleteAllQuotations -> {
                findNavController().navigate(R.id.action_favouritesFragment_to_deleteAllDialog)
                true
            }
            else -> false
        }
    }

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
        val deleteAllItem = menu.findItem(R.id.deleteAllQuotations)
        deleteAllItem?.isVisible = !viewModel.favoriteQuotations.value.isNullOrEmpty()
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.END) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false // No permitimos arrastrar elementos
        }

        override fun isLongPressDragEnabled(): Boolean {
            return false // Deshabilitamos drag & drop
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return true // Habilitamos el swipe
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            viewModel.deleteQuotationAtPosition(position) // Eliminamos la cita en la posición
        }
    })

}