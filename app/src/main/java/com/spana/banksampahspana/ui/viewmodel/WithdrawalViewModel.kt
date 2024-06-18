package com.spana.banksampahspana.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.spana.banksampahspana.data.repository.WithdrawalRepository

class WithdrawalViewModel(private val withdrawalRepository: WithdrawalRepository) : ViewModel() {
    fun withdrawal(totalWithdrawal: Int) = withdrawalRepository.withdrawal(totalWithdrawal)

    fun getUserTotalWithdrawal() = withdrawalRepository.getUserTotalWithdrawal()

    fun getUserWithdrawalHistories() = withdrawalRepository.getUserWithdrawalHistories()
}