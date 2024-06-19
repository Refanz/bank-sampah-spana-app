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
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.model.User
import com.spana.banksampahspana.databinding.FragmentProfileBinding
import com.spana.banksampahspana.ui.view.activity.WelcomeActivity
import com.spana.banksampahspana.ui.viewmodel.AuthViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory
import com.spana.banksampahspana.util.Utils.toEditable

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding

    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = obtainAuthViewModel(requireContext() as AppCompatActivity)

        initGenderComboBox()

        initPaymentMethodComboBox()

        setProfile()

        binding?.btnLogout?.setOnClickListener {
            showDialogLogout()
        }

        binding?.btnSaveEditProfile?.setOnClickListener {
            updateUserInfo()
        }
    }

    private fun showDialogLogout() {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle("Logout")
            setMessage("Anda ingin logout?")
            setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            setPositiveButton("Ya") { _, _ ->

                authViewModel.userLogout().observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> {}

                        is Result.Success -> {
                            val intent = Intent(requireActivity(), WelcomeActivity::class.java)
                            startActivity(intent)

                            requireActivity().finish()
                        }

                        is Result.Error -> {}
                    }
                }


            }

        }.show()
    }

    private fun updateUserInfo() {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle("Update User Info")
            setMessage("Anda ingin mengubah data?")
            setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            setPositiveButton("Ya") { _, _ ->

                val name = binding?.inputProfileName?.text.toString()
                val email = binding?.inputProfileEmail?.text.toString()
                val nis = binding?.inputProfileNis?.text.toString()
                val userClass = binding?.inputProfileClass?.text.toString().toInt()
                val phone = binding?.inputProfilePhone?.text.toString()
                val paymentMethod = binding?.inputProfilePayment?.text.toString()
                val gender = binding?.inputProfileGender?.text.toString()

                val user = User(
                    name = name,
                    email = email,
                    nis = nis,
                    studentClass = userClass,
                    phone = phone,
                    paymentMethod = paymentMethod,
                    gender = gender
                )

                authViewModel.updateUserInfo(user).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            authViewModel.userLogout().observe(viewLifecycleOwner) { result ->
                                when (result) {
                                    is Result.Loading -> {}

                                    is Result.Success -> {
                                        showLoading(false)

                                        val intent =
                                            Intent(requireActivity(), WelcomeActivity::class.java)
                                        startActivity(intent)

                                        requireActivity().finish()
                                    }

                                    is Result.Error -> {
                                        showLoading(false)
                                    }
                                }
                            }
                        }

                        is Result.Error -> {
                            showLoading(false)
                        }
                    }
                }


            }

        }.show()
    }

    private fun setProfile() {
        authViewModel.getUserInfo().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)

                    val user = result.data?.user

                    binding?.inputProfileName?.text = user?.name?.toEditable()
                    binding?.inputProfileEmail?.text = user?.email?.toEditable()
                    binding?.inputProfileNis?.text = user?.userDetail?.nis?.toEditable()
                    binding?.inputProfileClass?.text =
                        user?.userDetail?.userClass?.toString()?.toEditable()
                    binding?.inputProfilePhone?.text = user?.userDetail?.phone?.toEditable()
                    binding?.inputProfilePayment?.setText(user?.userDetail?.paymentMethod, false)
                    binding?.inputProfileGender?.setText(user?.userDetail?.gender, false)
                }

                is Result.Error -> {
                    showLoading(false)
                    showToast(result.error)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarProfile?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}