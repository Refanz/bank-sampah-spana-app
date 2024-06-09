package com.spana.banksampahspana.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.model.User
import com.spana.banksampahspana.databinding.ActivityLoginBinding
import com.spana.banksampahspana.ui.viewmodel.AuthViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        authViewModel = obtainViewModel(this)

        binding?.btnLogin?.setOnClickListener {
            checkFields()
        }
    }

    private fun initBinding() {
        _binding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)
    }

    private fun checkFields() {
        val email = binding?.inputEmail?.text.toString()
        val password = binding?.inputPassword?.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            showToast("Isi semua field!")
        } else {
            authViewModel.login(email, password).observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)

                        val userResult = result.data.user
                        val user = User(
                            id = userResult.id,
                            name = userResult.name,
                            email = userResult.email,
                            role = userResult.role,
                        )

                        authViewModel.saveAuthToken(result.data.accessToken)
                        authViewModel.saveAuthUser(user)

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    }

                    is Result.Error -> {
                        showLoading(false)
                        showToast(result.error)
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isVisible: Boolean) {
        binding?.loginProgressBar?.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun obtainViewModel(activity: AppCompatActivity): AuthViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AuthViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}