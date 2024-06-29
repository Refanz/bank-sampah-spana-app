package com.spana.banksampahspana.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.databinding.ActivityResetPasswordBinding
import com.spana.banksampahspana.ui.viewmodel.AuthViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory

class ResetPasswordActivity : AppCompatActivity() {

    private var _binding: ActivityResetPasswordBinding? = null
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
            val token = intent?.data?.path.toString().split("/").last()
            val email = binding?.inputEmail?.text
            val password = binding?.inputPassword?.text
            val passwordConfirm = binding?.inputPasswordConfirm?.text

            if (email?.isEmpty() == true || password?.isEmpty() == true || passwordConfirm?.isEmpty() == true) {
                Toast.makeText(this, "Isi semua field!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            resetPassword(token, email.toString(), password.toString(), passwordConfirm.toString())
        }
    }

    private fun initBinding() {
        _binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    private fun obtainAuthViewModel(activity: AppCompatActivity): AuthViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AuthViewModel::class.java]
    }

    private fun resetPassword(
        token: String,
        email: String,
        password: String,
        passwordConfirm: String
    ) {
        authViewModel.resetPassword(token, email, password, passwordConfirm)
            .observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)
                        showToast(result.data.toString())

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    is Result.Error -> {
                        showLoading(false)
                        showToast(result.error)
                    }
                }
            }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarResetPassword?.visibility =
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