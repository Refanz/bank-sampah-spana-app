package com.spana.banksampahspana.ui.view.fragment

import android.content.Intent
import android.os.Bundle
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
import com.spana.banksampahspana.data.remote.response.TrashCategoryItem
import com.spana.banksampahspana.databinding.FragmentHomeBinding
import com.spana.banksampahspana.databinding.WithdrawalDialogViewBinding
import com.spana.banksampahspana.ui.adapter.TrashCategoryAdapter
import com.spana.banksampahspana.ui.view.activity.WithdrawalHistoryActivity
import com.spana.banksampahspana.ui.view.activity.TrashAddActivity
import com.spana.banksampahspana.ui.viewmodel.AuthViewModel
import com.spana.banksampahspana.ui.viewmodel.TrashViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory
import com.spana.banksampahspana.ui.viewmodel.WithdrawalViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private lateinit var authViewModel: AuthViewModel
    private lateinit var trashViewModel: TrashViewModel
    private lateinit var withdrawalViewModel: WithdrawalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = obtainViewModel(requireContext() as AppCompatActivity)
        trashViewModel = obtainTrashViewModel(requireContext() as AppCompatActivity)
        withdrawalViewModel = obtainWithdrawalViewModel(requireContext() as AppCompatActivity)

        binding?.btnAddTrash?.setOnClickListener {
            val intent = Intent(requireContext(), TrashAddActivity::class.java)
            startActivity(intent)
        }

        authViewModel.getAuthUser().observe(viewLifecycleOwner) { user ->
            binding?.txtUser?.text = "Hai, ${user.name}"
        }

        authViewModel.getUserInfo().observe(viewLifecycleOwner) { user ->
            when (user) {
                is Result.Loading -> {}

                is Result.Success -> {
                    val balance = user.data?.userDetail?.balance
                    binding?.txtBalanceVal?.text = "Rp. $balance"
                }

                is Result.Error -> {}
            }
        }

        initRecyclerView()

        trashViewModel.getTrashCategory().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}

                is Result.Success -> {
                    setRecyclerViewData(result.data as ArrayList<TrashCategoryItem>)
                }

                is Result.Error -> {
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding?.txtTransactionDetail?.setOnClickListener {
            val intent = Intent(requireActivity(), WithdrawalHistoryActivity::class.java)
            startActivity(intent)
        }

        binding?.btnWithdrawal?.setOnClickListener {
            showWithdrawalDialog()
        }

        setTotalWithdrawal()
    }

    private fun initRecyclerView() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.rvTrashCategory?.layoutManager = layoutManager
    }

    private fun setRecyclerViewData(trashCategoryList: ArrayList<TrashCategoryItem>) {
        val adapter = TrashCategoryAdapter(trashCategoryList)
        binding?.rvTrashCategory?.adapter = adapter
    }

    private fun showWithdrawalDialog() {
        val withdrawalDialogView =
            WithdrawalDialogViewBinding.inflate(LayoutInflater.from(requireContext()))

        withdrawalDialogView.btnWithdrawal.setOnClickListener {
            val inputWithdrawal = withdrawalDialogView.editTextAmount.text

            val totalWithdrawal: Int = if (inputWithdrawal?.isEmpty() == true) {
                0
            } else {
                inputWithdrawal.toString().toInt()
            }

            if (totalWithdrawal == 0) {
                withdrawalDialogView.inputLayoutAmount.error = "Isi jumlah penarikan!"
                return@setOnClickListener
            }

            if (totalWithdrawal < 10000) {
                withdrawalDialogView.inputLayoutAmount.error = "Minimal Rp.10000"
                return@setOnClickListener
            }

            withdrawal(totalWithdrawal)
        }

        MaterialAlertDialogBuilder(requireContext()).apply {
            setView(withdrawalDialogView.root)
            setTitle("Menu Penarikan")
            setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }.show()
        }
    }

    private fun withdrawal(totalWithdrawal: Int) {
        withdrawalViewModel.withdrawal(totalWithdrawal).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}

                is Result.Success -> {
                    Toast.makeText(requireContext(), "Berhasil tarik tunai!", Toast.LENGTH_SHORT)
                        .show()
                }

                is Result.Error -> {
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setTotalWithdrawal() {
        withdrawalViewModel.getUserTotalWithdrawal().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}

                is Result.Success -> {
                    binding?.txtValWithdrawal?.text = "Rp.${result.data}"
                }

                is Result.Error -> {}
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): AuthViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AuthViewModel::class.java]
    }

    private fun obtainTrashViewModel(activity: AppCompatActivity): TrashViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[TrashViewModel::class.java]
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