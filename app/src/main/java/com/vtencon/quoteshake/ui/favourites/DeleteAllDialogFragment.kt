package com.vtencon.quoteshake.ui.favourites

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.vtencon.quoteshake.R
import kotlin.getValue


class DeleteAllDialogFragment : androidx.fragment.app.DialogFragment() {
    private val viewModel: FavouritesViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).setTitle(getString(R.string.delete_all_favourite_quotations)).setMessage(
            getString(
                R.string.do_you_really_want_to_delete_all_your
            ) +
                getString(R.string.quotations)).setPositiveButton(getString(R.string.yes)){ _, _ ->
            viewModel.deleteAllQuotations()
        }.setNegativeButton(getString(R.string.no)) { _, _ -> dismiss()}.create()
    }
}