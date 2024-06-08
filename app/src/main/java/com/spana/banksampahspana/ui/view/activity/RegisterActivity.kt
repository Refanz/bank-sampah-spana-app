package com.spana.banksampahspana.ui.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.model.User
import com.spana.banksampahspana.databinding.ActivityRegisterBinding
import com.spana.banksampahspana.ui.viewmodel.AuthViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        authViewModel = obtainViewModel(this)

        initGenderComboBox()

        initPaymentMethodComboBox()

        binding?.btnRegister?.setOnClickListener {
            val name = binding?.inputName?.text.toString()
            val email = binding?.inputEmail?.text.toString()
            val password = binding?.inputPassword?.text.toString()
            val paymentMethod = binding?.inputPaymentMethod?.text.toString()
            val gender = binding?.inputGender?.text.toString()
            val phone = binding?.inputPhone?.text.toString()
            val studentClass = binding?.inputClass?.text.toString().toInt()
            val nis = binding?.inputNis?.text.toString()

            val user = User(
                name = name,
                phone = phone,
                gender = gender,
                password = password,
                email = email,
                paymentMethod = paymentMethod,
                studentClass = studentClass,
                nis = nis
            )

            authViewModel.register(user).observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)
                        showToast(result.data.toString())
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
        _binding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)
    }

    private fun initGenderComboBox() {
        val genderItems = arrayOf("Laki - Laki", "Perempuan")
        (binding?.inputLayoutGender?.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(
            genderItems
        )
    }

    private fun initPaymentMethodComboBox() {
        val paymentMethods = arrayOf("OVO", "Gopay", "Dana")
        (binding?.inputLayoutPaymentMethod?.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(
            paymentMethods
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isVisible: Boolean) {
        binding?.progressBarRegister?.visibility = if (isVisible) View.VISIBLE else View.GONE
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