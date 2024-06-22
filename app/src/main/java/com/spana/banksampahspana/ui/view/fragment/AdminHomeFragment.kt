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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.databinding.FragmentAdminHomeBinding
import com.spana.banksampahspana.ui.view.activity.TrashAddAdminActivity
import com.spana.banksampahspana.ui.view.activity.TrashCategoryAddActivity
import com.spana.banksampahspana.ui.viewmodel.AuthViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory
import com.spana.banksampahspana.util.Utils.saveFile

class AdminHomeFragment : Fragment() {

    private var _binding: FragmentAdminHomeBinding? = null
    private val binding get() = _binding

    private lateinit var authViewModel: AuthViewModel

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
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle("Riwayat Penarikan Siswa")
            setMessage("Mengunduh data penarikan siswa?")
            setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            setPositiveButton("Ya") { _, _ ->
                downloadWithdrawalHistories()
            }
        }.show()
    }

    private fun downloadWithdrawalHistories() {
        authViewModel.downloadUserWithdrawalHistories().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    val file = saveFile(result.data)
                    showLoading(false)
                    showToast("Laporan diunduh: ${file.absolutePath}")
                }

                is Result.Error -> {
                    showToast(result.error)
                    showLoading(false)
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

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarHome?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}