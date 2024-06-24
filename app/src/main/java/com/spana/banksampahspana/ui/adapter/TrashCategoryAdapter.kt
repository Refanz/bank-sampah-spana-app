package com.spana.banksampahspana.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spana.banksampahspana.data.remote.response.TrashCategoryItem
import com.spana.banksampahspana.databinding.TrashCategoryItemBinding

class TrashCategoryAdapter(private val trashCategoryList: ArrayList<TrashCategoryItem>) :
    RecyclerView.Adapter<TrashCategoryAdapter.TrashCategoryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrashCategoryAdapter.TrashCategoryViewHolder {
        val binding =
            TrashCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrashCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TrashCategoryAdapter.TrashCategoryViewHolder,
        position: Int
    ) {
        val trashCategory = trashCategoryList[position]

        holder.binding.txtTrashCategoryName.text = trashCategory.name
        holder.binding.txtTrashCategoryPrice.text = "${trashCategory.price}/kg"
    }

    override fun getItemCount(): Int {
        return trashCategoryList.size
    }

    inner class TrashCategoryViewHolder(var binding: TrashCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}