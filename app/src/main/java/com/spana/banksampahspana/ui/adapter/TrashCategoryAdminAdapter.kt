package com.spana.banksampahspana.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spana.banksampahspana.data.remote.response.TrashCategoryItem
import com.spana.banksampahspana.databinding.TrashCategoryAdminItemBinding

class TrashCategoryAdminAdapter(private val trashCategories: ArrayList<TrashCategoryItem>) :
    RecyclerView.Adapter<TrashCategoryAdminAdapter.TrashCategoryAdminViewHolder>() {

    private lateinit var trashCategoryActionCallback: TrashCategoryActionCallback

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
        holder.binding.txtTrashCategoryPriceVal.text = trashCategory.price.toString()

        holder.binding.btnEditTrashCategory.setOnClickListener {
            this.trashCategoryActionCallback.onUpdate(trashCategory)
        }

        holder.binding.btnDeleteTrashCategory.setOnClickListener {
            this.trashCategoryActionCallback.onDelete(trashCategory.id ?: 0)
        }
    }

    override fun getItemCount(): Int {
        return trashCategories.size
    }

    inner class TrashCategoryAdminViewHolder(val binding: TrashCategoryAdminItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setTrashCategoryActionCallback(trashCategoryActionCallback: TrashCategoryActionCallback) {
        this.trashCategoryActionCallback = trashCategoryActionCallback
    }

    interface TrashCategoryActionCallback {
        fun onUpdate(trashCategoryItem: TrashCategoryItem)
        fun onDelete(id: Int)
    }
}