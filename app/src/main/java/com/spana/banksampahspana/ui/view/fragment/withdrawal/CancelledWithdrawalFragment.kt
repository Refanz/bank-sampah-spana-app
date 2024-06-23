package com.spana.banksampahspana.ui.view.fragment.withdrawal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.remote.response.WithdrawalAdmin
import com.spana.banksampahspana.databinding.FragmentCancelledWithdrawalBinding
import com.spana.banksampahspana.ui.adapter.WithdrawalAdminCancelledAdapter
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory
import com.spana.banksampahspana.ui.viewmodel.WithdrawalViewModel

class CancelledWithdrawalFragment : Fragment() {

    private var _binding: FragmentCancelledWithdrawalBinding? = null
    private val binding get() = _binding

    private lateinit var withdrawalViewModel: WithdrawalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCancelledWithdrawalBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withdrawalViewModel = obtainWithdrawalViewModel(requireActivity() as AppCompatActivity)
        setRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding?.rvWithdrawalHistories?.layoutManager = layoutManager

        withdrawalViewModel.getUSerWithdrawalHistoriesByStatus("cancelled")
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)
                        setRecyclerViewData(result.data as ArrayList<WithdrawalAdmin>)
                    }

                    is Result.Error -> {
                        showLoading(false)
                    }
                }
            }
    }

    private fun setRecyclerViewData(data: ArrayList<WithdrawalAdmin>) {
        val adapter =
            WithdrawalAdminCancelledAdapter(data)
        binding?.rvWithdrawalHistories?.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarWithdrawal?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun obtainWithdrawalViewModel(activity: AppCompatActivity): WithdrawalViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[WithdrawalViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}