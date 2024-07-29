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
import com.spana.banksampahspana.data.model.Admin
import com.spana.banksampahspana.databinding.FragmentAdminProfileBinding
import com.spana.banksampahspana.ui.view.activity.ChangePasswordActivity
import com.spana.banksampahspana.ui.view.activity.WelcomeActivity
import com.spana.banksampahspana.ui.viewmodel.AuthViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory
import com.spana.banksampahspana.util.Utils.toEditable

class AdminProfileFragment : Fragment() {

    private var _binding: FragmentAdminProfileBinding? = null
    private val binding get() = _binding

    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = obtainAuthViewModel(requireActivity() as AppCompatActivity)

        initGenderComboBox()

        setProfile()

        binding?.btnLogout?.setOnClickListener {
            showDialogLogout()
        }

        binding?.btnSaveEditProfile?.setOnClickListener {
            updateAdminProfile()
        }

        binding?.btnChangePassword?.setOnClickListener {
            val intent = Intent(requireContext(), ChangePasswordActivity::class.java)
            intent.putExtra(ACTIVITY_EXTRA, "admin")
            startActivity(intent)
        }
    }

    private fun setProfile() {
        authViewModel.getAdminInfo().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)

                    val user = result.data?.admin

                    binding?.inputProfileName?.text = user?.name?.toEditable()
                    binding?.inputProfileNip?.text = user?.adminDetail?.nip?.toEditable()
                    binding?.inputProfileEmail?.text = user?.email?.toEditable()
                    binding?.inputProfilePhone?.text = user?.adminDetail?.phone?.toEditable()
                    binding?.inputProfileGender?.setText(user?.adminDetail?.gender, false)
                }

                is Result.Error -> {
                    showLoading(false)
                    showToast(result.error)
                }
            }
        }
    }

    private fun updateAdminProfile() {
        val name = binding?.inputProfileName?.text.toString()
        val email = binding?.inputProfileEmail?.text.toString()
        val nip = binding?.inputProfileNip?.text.toString()
        val gender = binding?.inputProfileGender?.text.toString()
        val phone = binding?.inputProfilePhone?.text.toString()

        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle("Update Admin Info")
            setMessage("Anda ingin mengubah data?")
            setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            setPositiveButton("Ya") { _, _ ->

                val admin = Admin(
                    gender = gender,
                    phone = phone,
                    nip = nip,
                    name = name,
                    email = email,
                )

                authViewModel.updateAdminInfo(admin).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            authViewModel.adminLogout().observe(viewLifecycleOwner) { logout ->
                                when (logout) {
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

    private fun initGenderComboBox() {
        val genderItems = arrayOf("Laki - Laki", "Perempuan")
        (binding?.inputProfileGender as? MaterialAutoCompleteTextView)?.setSimpleItems(
            genderItems
        )
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarProfile?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showDialogLogout() {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle("Logout")
            setMessage("Anda ingin logout?")
            setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            setPositiveButton("Ya") { _, _ ->

                authViewModel.adminLogout().observe(viewLifecycleOwner) { result ->
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

    private fun obtainAuthViewModel(activity: AppCompatActivity): AuthViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AuthViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ACTIVITY_EXTRA = "activity_extra"
    }
}