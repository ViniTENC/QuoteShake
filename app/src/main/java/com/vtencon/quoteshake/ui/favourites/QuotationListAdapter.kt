package com.vtencon.quoteshake.ui.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vtencon.quoteshake.databinding.QuotationItemBinding
import com.vtencon.quoteshake.ui.domain.model.Quotation

class QuotationListAdapter(private val onItemClick: (String) -> Unit) : ListAdapter<Quotation, ViewHolder>(QuotationDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(QuotationItemBinding.inflate(LayoutInflater.from(parent.context), parent,
            false), onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    object QuotationDiff:  DiffUtil.ItemCallback<Quotation>() {
        // para poder chequear si ya existe ese quotation
        override fun areItemsTheSame(
            oldItem: Quotation,
            newItem: Quotation
        ): Boolean {
            return (oldItem.id == newItem.id)
        }

        override fun areContentsTheSame(
            oldItem: Quotation,
            newItem: Quotation
        ): Boolean {
            return(oldItem.text == newItem.text)
        }
    }
}
class ViewHolder(private val binding: QuotationItemBinding, private val onItemClick: (String) -> Unit) : RecyclerView.ViewHolder(binding.root){
    fun bind(quotation: Quotation){
        binding.tvQuotationText.text = quotation.text
        binding.tvQuotationAuthor.text = quotation.author // Asegura que se asigna el autor correctamente
    }
    init {
        binding.root.setOnClickListener {
            onItemClick(binding.tvQuotationAuthor.text.toString())
        }
    }
}