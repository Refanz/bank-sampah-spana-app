package com.spana.banksampahspana.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spana.banksampahspana.data.remote.response.Trash
import com.spana.banksampahspana.databinding.TrashUserItemBinding

class TrashAdapter(private val trashes: ArrayList<Trash>) :
    RecyclerView.Adapter<TrashAdapter.TrashViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrashAdapter.TrashViewHolder {
        val binding =
            TrashUserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrashViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrashAdapter.TrashViewHolder, position: Int) {
        val trash = trashes[position]

        holder.binding.txtTrashTypeVal.text = trash.trashType
        holder.binding.txtTrashDateVal.text = trash.createdAt
        holder.binding.txtTrashWeightVal.text = "${trash.weight} kg"
        holder.binding.txtTrashPriceVal.text = "Rp.${trash.totalDeposit}"
    }

    override fun getItemCount(): Int {
        return trashes.size
    }

    inner class TrashViewHolder(var binding: TrashUserItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}