package com.spana.banksampahspana.ui.view.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.remote.response.User
import com.spana.banksampahspana.databinding.ActivityUserUpdateBinding
import com.spana.banksampahspana.ui.view.fragment.UsersFragment
import com.spana.banksampahspana.ui.viewmodel.AuthViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory
import com.spana.banksampahspana.util.Utils.toEditable

class UserUpdateActivity : AppCompatActivity() {

    private var _binding: ActivityUserUpdateBinding? = null
    private val binding get() = _binding

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        initGenderComboBox()

        initPaymentMethodComboBox()

        authViewModel = obtainAuthViewModel(this)

        setProfile()

        binding?.topAppBar?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding?.btnSaveEditProfile?.setOnClickListener {
            updateUserInfo()
        }
    }

    private fun initBinding() {
        _binding = ActivityUserUpdateBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    private fun updateUserInfo() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle("Update User Info")
            setMessage("Anda ingin mengubah data?")
            setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            setPositiveButton("Ya") { _, _ ->

                val id = getDataIntent().id
                val name = binding?.inputProfileName?.text.toString()
                val email = binding?.inputProfileEmail?.text.toString()
                val nis = binding?.inputProfileNis?.text.toString()
                val userClass = binding?.inputProfileClass?.text.toString().toInt()
                val phone = binding?.inputProfilePhone?.text.toString()
                val paymentMethod = binding?.inputProfilePayment?.text.toString()
                val gender = binding?.inputProfileGender?.text.toString()

                val user = com.spana.banksampahspana.data.model.User(
                    id = id,
                    name = name,
                    email = email,
                    nis = nis,
                    studentClass = userClass,
                    phone = phone,
                    paymentMethod = paymentMethod,
                    gender = gender
                )

                authViewModel.updateUserAdmin(user).observe(this@UserUpdateActivity) { result ->
                    when (result) {

                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            showLoading(false)
                            showToast("Berhasil mengupdate data user")
                        }

                        is Result.Error -> {
                            showLoading(false)
                            showToast(result.error)
                        }
                    }
                }
            }
        }.show()
    }

    private fun setProfile() {
        binding?.inputProfileName?.text = getDataIntent().name.toEditable()
        binding?.inputProfileEmail?.text = getDataIntent().email.toEditable()
        binding?.inputProfileNis?.text = getDataIntent().userDetail?.nis?.toEditable()
        binding?.inputProfileClass?.text =
            getDataIntent().userDetail?.userClass?.toString()?.toEditable()
        binding?.inputProfilePhone?.text = getDataIntent().userDetail?.phone?.toEditable()
        binding?.inputProfilePayment?.setText(getDataIntent().userDetail?.paymentMethod, false)
        binding?.inputProfileGender?.setText(getDataIntent().userDetail?.gender, false)
    }

    private fun getDataIntent(): User {
        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(UsersFragment.USER_EXTRA, User::class.java)
        } else {
            intent.getParcelableExtra(UsersFragment.USER_EXTRA)
        }

        return data ?: User()
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarProfile?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun initGenderComboBox() {
        val genderItems = arrayOf("Laki - Laki", "Perempuan")
        (binding?.inputProfileGender as? MaterialAutoCompleteTextView)?.setSimpleItems(
            genderItems
        )
    }

    private fun initPaymentMethodComboBox() {
        val paymentMethods = arrayOf("OVO", "Gopay", "Dana")
        (binding?.inputProfilePayment as? MaterialAutoCompleteTextView)?.setSimpleItems(
            paymentMethods
        )
    }

    private fun obtainAuthViewModel(activity: AppCompatActivity): AuthViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AuthViewModel::class]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}