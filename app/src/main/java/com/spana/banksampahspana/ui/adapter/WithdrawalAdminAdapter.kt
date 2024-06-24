package com.spana.banksampahspana.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spana.banksampahspana.data.remote.response.WithdrawalAdmin
import com.spana.banksampahspana.databinding.WithdrawalAdminItemBinding

class WithdrawalAdminAdapter(private val withdrawalHistories: ArrayList<WithdrawalAdmin>) :
    RecyclerView.Adapter<WithdrawalAdminAdapter.WithdrawalAdminViewHolder>() {

    private lateinit var withdrawalAdminActionCallback: WithdrawalAdminActionCallback

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WithdrawalAdminAdapter.WithdrawalAdminViewHolder {
        val binding =
            WithdrawalAdminItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WithdrawalAdminViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: WithdrawalAdminAdapter.WithdrawalAdminViewHolder,
        position: Int
    ) {
        val withdrawalHistory = withdrawalHistories[position]

        val id = withdrawalHistory.withdrawalId
        val userId = withdrawalHistory.userId

        holder.binding.txtUserNameVal.text = withdrawalHistory.name
        holder.binding.txtUserNisVal.text = withdrawalHistory.nis
        holder.binding.txtUserPhoneVal.text = withdrawalHistory.phone
        holder.binding.txtUserPaymentMethodVal.text = withdrawalHistory.payment
        holder.binding.txtWithdrawalDateVal.text = withdrawalHistory.withdrawalDate
        holder.binding.txtStatusWithdrawalVal.text = withdrawalHistory.status
        holder.binding.txtTotalWithdrawalVal.text = "Rp. ${withdrawalHistory.totalWithdrawal}"

        holder.binding.btnProcessWithdrawal.setOnClickListener {
            withdrawalAdminActionCallback.onProcess(id, userId)
        }

        holder.binding.btnCancelWithdrawal.setOnClickListener {
            withdrawalAdminActionCallback.onCancel(id, userId)
        }
    }

    override fun getItemCount(): Int {
        return withdrawalHistories.size
    }

    fun setOnWithdrawalAdminActionCallback(withdrawalAdminActionCallback: WithdrawalAdminActionCallback) {
        this.withdrawalAdminActionCallback = withdrawalAdminActionCallback
    }

    inner class WithdrawalAdminViewHolder(val binding: WithdrawalAdminItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface WithdrawalAdminActionCallback {
        fun onProcess(id: Int, userId: Int)
        fun onCancel(id: Int, userId: Int)
    }
}