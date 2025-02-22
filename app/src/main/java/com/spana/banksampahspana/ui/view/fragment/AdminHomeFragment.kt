package com.spana.banksampahspana.ui.view.fragment

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.databinding.FragmentAdminHomeBinding
import com.spana.banksampahspana.databinding.TransanctionDownloadDilalogBinding
import com.spana.banksampahspana.ui.view.activity.TrashAddAdminActivity
import com.spana.banksampahspana.ui.view.activity.TrashCategoryAddActivity
import com.spana.banksampahspana.ui.viewmodel.AuthViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory
import com.spana.banksampahspana.util.Utils.saveFile

class AdminHomeFragment : Fragment() {

    private var _binding: FragmentAdminHomeBinding? = null
    private val binding get() = _binding

    private lateinit var authViewModel: AuthViewModel

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                showToast("Notifications permission granted")
            } else {
                showToast("Notifications permission rejected")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = obtainAuthViewModel(requireActivity() as AppCompatActivity)

        if (Build.VERSION.SDK_INT >= 33) {
            requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        authViewModel.getAuthUser().observe(viewLifecycleOwner) { user ->
            binding?.txtAdmin?.text = "Hai, ${user.name}"
        }

        binding?.btnAddTrash?.setOnClickListener {
            val intent = Intent(requireActivity(), TrashAddAdminActivity::class.java)
            startActivity(intent)
        }

        binding?.btnAddTrashCategory?.setOnClickListener {
            val intent = Intent(requireActivity(), TrashCategoryAddActivity::class.java)
            startActivity(intent)
        }

        binding?.btnDownload?.setOnClickListener {
            showDownloadDialog()
        }
    }

    private fun obtainAuthViewModel(activity: AppCompatActivity): AuthViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AuthViewModel::class.java]
    }

    private fun showDownloadDialog() {
        val downloadDialog =
            TransanctionDownloadDilalogBinding.inflate(LayoutInflater.from(requireContext()))

        val months = arrayOf(
            "Januari",
            "Februari",
            "Maret",
            "April",
            "Mei",
            "Juni",
            "Juli",
            "Agustus",
            "September",
            "Oktober",
            "November",
            "Desember"
        )

        (downloadDialog.inputLayoutMonth.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(
            months
        )

        downloadDialog.btnDownloadAll.setOnClickListener {
            downloadWithdrawalHistories(downloadDialog)
        }

        downloadDialog.btnDownloadByMonth.setOnClickListener {
            val month = when (downloadDialog.inputMonth.text.toString()) {
                "Januari" -> 1
                "Februari" -> 2
                "Maret" -> 3
                "April" -> 4
                "Mei" -> 5
                "Juni" -> 6
                "Juli" -> 7
                "Agustus" -> 8
                "September" -> 9
                "Oktober" -> 10
                "November" -> 11
                "Desember" -> 12
                else -> {
                    0
                }
            }

            downloadTransactionHistoryByMonth(downloadDialog, month)
        }

        MaterialAlertDialogBuilder(requireContext()).apply {
            setView(downloadDialog.root)
            setTitle("Riwayat Transaksi Siswa")
            setMessage("Mengunduh data transaksi siswa?")
            setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    private fun downloadTransactionHistoryByMonth(
        downloadDialog: TransanctionDownloadDilalogBinding,
        month: Int
    ) {
        authViewModel.downloadUserTransactionsByMonth(month).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(downloadDialog, true)
                }

                is Result.Success -> {
                    val file = saveFile(result.data)
                    showLoading(downloadDialog, false)
                    showToast("Laporan diunduh: ${file.absolutePath}")
                }

                is Result.Error -> {
                    showToast(result.error)
                    showLoading(downloadDialog, false)
                }
            }
        }
    }

    private fun downloadWithdrawalHistories(downloadDialog: TransanctionDownloadDilalogBinding) {
        authViewModel.downloadUserWithdrawalHistories().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(downloadDialog, true)
                }

                is Result.Success -> {
                    val file = saveFile(result.data)
                    showLoading(downloadDialog, false)
                    showToast("Laporan diunduh: ${file.absolutePath}")
                }

                is Result.Error -> {
                    showToast(result.error)
                    showLoading(downloadDialog, false)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showLoading(
        downloadDialog: TransanctionDownloadDilalogBinding,
        isLoading: Boolean
    ) {
        downloadDialog.progressBarDownload.visibility =
            if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}