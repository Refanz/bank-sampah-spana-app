package com.spana.banksampahspana.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spana.banksampahspana.data.remote.response.WithdrawalAdmin
import com.spana.banksampahspana.databinding.WithdrawalAdminCancelledItemBinding


class WithdrawalAdminCancelledAdapter(private val withdrawalHistories: ArrayList<WithdrawalAdmin>) :
    RecyclerView.Adapter<WithdrawalAdminCancelledAdapter.WithdrawalAdminCancelledViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WithdrawalAdminCancelledAdapter.WithdrawalAdminCancelledViewHolder {
        val binding = WithdrawalAdminCancelledItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WithdrawalAdminCancelledViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: WithdrawalAdminCancelledAdapter.WithdrawalAdminCancelledViewHolder,
        position: Int
    ) {
        val withdrawalHistory = withdrawalHistories[position]

        holder.binding.txtUserNameVal.text = withdrawalHistory.name
        holder.binding.txtUserNisVal.text = withdrawalHistory.nis
        holder.binding.txtUserPhoneVal.text = withdrawalHistory.phone
        holder.binding.txtUserPaymentMethodVal.text = withdrawalHistory.payment
        holder.binding.txtWithdrawalDateVal.text = withdrawalHistory.withdrawalDate
        holder.binding.txtStatusWithdrawalVal.text = withdrawalHistory.status
        holder.binding.txtTotalWithdrawalVal.text = "Rp. ${withdrawalHistory.totalWithdrawal}"
    }

    override fun getItemCount(): Int {
        return withdrawalHistories.size
    }

    inner class WithdrawalAdminCancelledViewHolder(val binding: WithdrawalAdminCancelledItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}