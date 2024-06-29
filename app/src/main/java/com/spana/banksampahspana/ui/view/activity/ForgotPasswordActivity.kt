package com.spana.banksampahspana.ui.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.databinding.ActivityForgotPasswordBinding
import com.spana.banksampahspana.ui.viewmodel.AuthViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory

class ForgotPasswordActivity : AppCompatActivity() {

    private var _binding: ActivityForgotPasswordBinding? = null
    private val binding get() = _binding

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        authViewModel = obtainAuthViewModel(this)

        binding?.topAppBar?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding?.btnSubmit?.setOnClickListener {
            val email = binding?.inputEmail?.text

            if (email?.isEmpty() == true) {
                Toast.makeText(this, "Email masih kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authViewModel.forgotPassword(email.toString()).observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)

                        MaterialAlertDialogBuilder(this).apply {
                            setTitle("Forgot Password")
                            setMessage(result.data.toString())
                            setPositiveButton("Ok") { dialog, _ ->
                                finish()
                                dialog.dismiss()
                            }
                        }.show()
                    }

                    is Result.Error -> {
                        showLoading(false)
                        showToast(result.error)
                    }
                }
            }
        }
    }

    private fun initBinding() {
        _binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    private fun obtainAuthViewModel(activity: AppCompatActivity): AuthViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AuthViewModel::class.java]
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarForgotPassword?.visibility =
            if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}