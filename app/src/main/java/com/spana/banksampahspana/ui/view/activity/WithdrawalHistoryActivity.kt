package com.spana.banksampahspana.ui.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.remote.response.Withdrawal
import com.spana.banksampahspana.databinding.ActivityWithdrawalHistoryBinding
import com.spana.banksampahspana.ui.adapter.WithdrawalAdapter
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory
import com.spana.banksampahspana.ui.viewmodel.WithdrawalViewModel

class WithdrawalHistoryActivity : AppCompatActivity() {

    private var _binding: ActivityWithdrawalHistoryBinding? = null
    private val binding get() = _binding

    private lateinit var withdrawalViewModel: WithdrawalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        withdrawalViewModel = obtainWithdrawalViewModel(this)

        binding?.topAppBar?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setRecyclerView()
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding?.rvWithdrawalHistory?.layoutManager = layoutManager

        withdrawalViewModel.getUserWithdrawalHistories().observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)

                    val adapter = WithdrawalAdapter(result.data as ArrayList<Withdrawal>)
                    binding?.rvWithdrawalHistory?.adapter = adapter
                }

                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initBinding() {
        _binding = ActivityWithdrawalHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarAddTrash?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainWithdrawalViewModel(activity: AppCompatActivity): WithdrawalViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[WithdrawalViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}