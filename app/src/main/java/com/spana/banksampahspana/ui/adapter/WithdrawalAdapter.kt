package com.spana.banksampahspana.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spana.banksampahspana.data.remote.response.Withdrawal
import com.spana.banksampahspana.databinding.WithdrawalHistoryItemBinding

class WithdrawalAdapter(private val withdrawalHistories: ArrayList<Withdrawal>) :
    RecyclerView.Adapter<WithdrawalAdapter.WithdrawalViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WithdrawalAdapter.WithdrawalViewHolder {
        val binding =
            WithdrawalHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WithdrawalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WithdrawalAdapter.WithdrawalViewHolder, position: Int) {
        val withdrawalHistory = withdrawalHistories[position]

        holder.binding.txtWithdrawalDateVal.text = withdrawalHistory.withdrawalDate
        holder.binding.txtStatusWithdrawalVal.text = withdrawalHistory.status
        holder.binding.txtTotalWithdrawalVal.text = "Rp.${withdrawalHistory.totalBalance}"
    }

    override fun getItemCount(): Int {
        return withdrawalHistories.size
    }

    inner class WithdrawalViewHolder(val binding: WithdrawalHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}