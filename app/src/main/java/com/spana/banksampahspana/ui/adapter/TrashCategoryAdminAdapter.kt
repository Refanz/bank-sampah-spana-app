package com.spana.banksampahspana.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spana.banksampahspana.data.remote.response.TrashCategoryItem
import com.spana.banksampahspana.databinding.TrashCategoryAdminItemBinding

class TrashCategoryAdminAdapter(private val trashCategories: ArrayList<TrashCategoryItem>) :
    RecyclerView.Adapter<TrashCategoryAdminAdapter.TrashCategoryAdminViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrashCategoryAdminAdapter.TrashCategoryAdminViewHolder {
        val binding = TrashCategoryAdminItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TrashCategoryAdminViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TrashCategoryAdminAdapter.TrashCategoryAdminViewHolder,
        position: Int
    ) {
        val trashCategory = trashCategories[position]

        holder.binding.txtTrashTypeVal.text = trashCategory.name
        holder.binding.txtTrashCategoryPriceVal.text = trashCategory.price
    }

    override fun getItemCount(): Int {
        return trashCategories.size
    }

    inner class TrashCategoryAdminViewHolder(val binding: TrashCategoryAdminItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}