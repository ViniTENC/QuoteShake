package com.vtencon.quoteshake.ui.favourites

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import kotlin.getValue


class DeleteAllDialogFragment : androidx.fragment.app.DialogFragment() {
    private val viewModel: FavouritesViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).setTitle("Delete all favourite quotations").setMessage("Do you really want to delete all your\n" +
                "quotations?").setPositiveButton("Yes"){ _, _ ->
            viewModel.deleteAllQuotations()
        }.setNegativeButton("No") { _, _ -> dismiss()}.create()
    }
}