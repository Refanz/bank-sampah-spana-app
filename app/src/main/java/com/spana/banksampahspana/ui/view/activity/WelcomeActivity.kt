package com.spana.banksampahspana.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.spana.banksampahspana.databinding.ActivityWelcomeBinding
import com.spana.banksampahspana.ui.viewmodel.AuthViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory

class WelcomeActivity : AppCompatActivity() {

    private var _binding: ActivityWelcomeBinding? = null
    private val binding get() = _binding

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        authViewModel = obtainViewModel(this)

        authViewModel.getAuthToken().observe(this@WelcomeActivity) { token ->
            if (token.isNotEmpty()) {
                authViewModel.getAuthUser().observe(this@WelcomeActivity) { user ->
                    if (user.role.lowercase() == "admin") {
                        val intent = Intent(this@WelcomeActivity, AdminActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    if (user.role.lowercase() == "user") {
                        val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }

        initBinding()

        binding?.btnMenuLogin?.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding?.btnMenuRegister?.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initBinding() {
        _binding = ActivityWelcomeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)
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