package com.spana.banksampahspana.ui.view.fragment.withdrawal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.remote.response.WithdrawalAdmin
import com.spana.banksampahspana.databinding.FragmentSubmittedWithdrawalBinding
import com.spana.banksampahspana.ui.adapter.WithdrawalAdminAdapter
import com.spana.banksampahspana.ui.viewmodel.NotificationViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory
import com.spana.banksampahspana.ui.viewmodel.WithdrawalViewModel

class SubmittedWithdrawalFragment : Fragment() {

    private var _binding: FragmentSubmittedWithdrawalBinding? = null
    private val binding get() = _binding

    private lateinit var withdrawalViewModel: WithdrawalViewModel
    private lateinit var notificationViewModel: NotificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubmittedWithdrawalBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withdrawalViewModel = obtainWithdrawalViewModel(requireActivity() as AppCompatActivity)
        notificationViewModel = obtainNotificationViewModel(requireActivity() as AppCompatActivity)

        setRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding?.rvWithdrawalHistories?.layoutManager = layoutManager

        withdrawalViewModel.getUSerWithdrawalHistoriesByStatus("submitted")
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
                        showToast("Data kosong!")
                    }
                }
            }
    }

    private fun setRecyclerViewData(data: ArrayList<WithdrawalAdmin>) {
        val adapter =
            WithdrawalAdminAdapter(data)

        adapter.setOnWithdrawalAdminActionCallback(object :
            WithdrawalAdminAdapter.WithdrawalAdminActionCallback {
            override fun onProcess(id: Int, userId: Int) {
                showDialogProcess(id, userId, "processing")
            }

            override fun onCancel(id: Int, userId: Int) {
                showDialogCancel(id, userId, "cancelled")
            }
        })

        binding?.rvWithdrawalHistories?.adapter = adapter
    }

    private fun showDialogProcess(id: Int, userId: Int, status: String) {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle("Penarikan Saldo")
            setMessage("Ingin memproses penarikan user?")
            setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            setPositiveButton("Ya") { _, _ ->
                processTransaction(id, status)
                sendNotification(
                    userId,
                    "Penarikan Saldo",
                    "Penarikan saldo anda sedang diproses oleh admin!"
                )
            }
        }.show()
    }

    private fun showDialogCancel(id: Int, userId: Int, status: String) {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle("Penarikan Saldo")
            setMessage("Ingin membatalkan penarikan user?")
            setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            setPositiveButton("Ya") { _, _ ->
                processTransaction(id, status)
                sendNotification(userId, "Penarikan Saldo", "Penarikan saldo anda dibatalkan!")
            }
        }.show()
    }

    private fun processTransaction(id: Int, status: String) {
        withdrawalViewModel.updateUserWithdrawalStatus(id, status)
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {}

                    is Result.Success -> {
                        showToast("Berhasil mengubah status penarikan id $id")
                        setRecyclerView()
                    }

                    is Result.Error -> {
                        showToast(result.error)
                    }
                }
            }
    }

    private fun sendNotification(id: Int, title: String, body: String) {
        notificationViewModel.sendNotification(id, title, body)
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {}

                    is Result.Success -> {
                        Log.d("Send Notification to User", result.data?.message.toString())
                    }

                    is Result.Error -> {}
                }
            }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarWithdrawal?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainWithdrawalViewModel(activity: AppCompatActivity): WithdrawalViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[WithdrawalViewModel::class.java]
    }

    private fun obtainNotificationViewModel(activity: AppCompatActivity): NotificationViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[NotificationViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}