package com.spana.banksampahspana.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.model.Admin
import com.spana.banksampahspana.databinding.ActivityRegisterAdminBinding
import com.spana.banksampahspana.ui.viewmodel.AuthViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory

class RegisterAdminActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterAdminBinding? = null
    private val binding get() = _binding

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        authViewModel = obtainViewModel(this)

        initGenderComboBox()

        binding?.btnRegister?.setOnClickListener {
            register()
        }

        binding?.txtExistingUser?.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initBinding() {
        _binding = ActivityRegisterAdminBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    private fun register() {
        val name = binding?.inputName?.text.toString()
        val email = binding?.inputEmail?.text.toString()
        val password = binding?.inputPassword?.text.toString()
        val passwordConfirm = binding?.inputPasswordConfirm?.text.toString()
        val gender = binding?.inputGender?.text.toString()
        val phone = binding?.inputPhone?.text.toString()
        val nip = binding?.inputNip?.text.toString()

        if (name.isEmpty() ||
            email.isEmpty() ||
            password.isEmpty() ||
            gender.isEmpty() ||
            phone.isEmpty() ||
            nip.isEmpty()
        ) {
            showToast("Isi semua field!")
            return
        }

        if (password != passwordConfirm) {
            showToast("Password dan konfirmasi password berbeda!")
            return
        }

        val admin = Admin(
            name = name,
            phone = phone,
            gender = gender,
            password = password,
            email = email,
            nip = nip,
        )

        authViewModel.registerAdmin(admin).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)

                    resetInput()

                    MaterialAlertDialogBuilder(this).apply {
                        setTitle("Registrasi Admin")
                        setMessage("Registrasi berhasil ingin login?")
                        setNegativeButton("Tidak") { dialog, _ ->
                            dialog.dismiss()
                        }
                        setPositiveButton("Ya") { _, _ ->
                            val intent =
                                Intent(this@RegisterAdminActivity, LoginActivity::class.java)
                            startActivity(intent)
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

    private fun initGenderComboBox() {
        val genderItems = arrayOf("Laki - Laki", "Perempuan")
        (binding?.inputLayoutGender?.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(
            genderItems
        )
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun resetInput() {
        binding?.inputName?.text = null
        binding?.inputEmail?.text = null
        binding?.inputPassword?.text = null
        binding?.inputPasswordConfirm?.text = null
        binding?.inputGender?.text = null
        binding?.inputPhone?.text = null
        binding?.inputNip?.text = null
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