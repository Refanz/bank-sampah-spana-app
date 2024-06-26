package com.spana.banksampahspana.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.databinding.ActivityChangePasswordBinding
import com.spana.banksampahspana.ui.viewmodel.AuthViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory

class ChangePasswordActivity : AppCompatActivity() {

    private var _binding: ActivityChangePasswordBinding? = null
    private val binding get() = _binding

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        authViewModel = obtainAuthViewModel(this)

        binding?.topAppBar?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding?.btnChangePassword?.setOnClickListener {
            val currentPassword = binding?.inputCurrentPassword?.text
            val newPassword = binding?.inputNewPassword?.text

            if (currentPassword?.isEmpty() == true || newPassword?.isEmpty() == true) {
                showToast("Isi semua field!")
                return@setOnClickListener
            }

            showDialogChangePassword(currentPassword.toString(), newPassword.toString())
        }
    }

    private fun initBinding() {
        _binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    private fun showDialogChangePassword(currentPassword: String, newPassword: String) {
        MaterialAlertDialogBuilder(this).apply {
            setTitle("Ganti Password")
            setMessage("Ingin mengganti password?")
            setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            setPositiveButton("Ya") { _, _ ->
                changePassword(currentPassword, newPassword)
            }
        }.show()
    }

    private fun changePassword(currentPassword: String, newPassword: String) {
        authViewModel.changePassword(currentPassword, newPassword).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {

                    showToast(result.data.toString())

                    val activity = intent.getStringExtra(ACTIVITY_EXTRA)

                    if (activity?.equals("admin") == true) {
                        adminLogout()
                    }

                    if (activity?.equals("user") == true) {
                        userLogout()
                    }

                }

                is Result.Error -> {
                    showLoading(false)
                    showToast("Ganti password gagal!")
                }
            }
        }
    }

    private fun adminLogout() {
        authViewModel.adminLogout().observe(this) { logout ->
            when (logout) {
                is Result.Loading -> {}

                is Result.Success -> {
                    showLoading(false)

                    val intent =
                        Intent(this, WelcomeActivity::class.java)
                    startActivity(intent)

                    finish()
                }

                is Result.Error -> {}
            }
        }
    }

    private fun userLogout() {
        authViewModel.userLogout().observe(this) { logout ->
            when (logout) {
                is Result.Loading -> {}

                is Result.Success -> {
                    showLoading(false)

                    val intent =
                        Intent(this, WelcomeActivity::class.java)
                    startActivity(intent)

                    finish()
                }

                is Result.Error -> {}
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarChangePassword?.visibility =
            if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainAuthViewModel(activity: AppCompatActivity): AuthViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AuthViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ACTIVITY_EXTRA = "activity_extra"
    }
}