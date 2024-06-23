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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.remote.response.User
import com.spana.banksampahspana.databinding.FragmentUsersBinding
import com.spana.banksampahspana.ui.adapter.UserAdapter
import com.spana.banksampahspana.ui.view.activity.UserUpdateActivity
import com.spana.banksampahspana.ui.viewmodel.AuthViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory

class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding

    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = obtainAuthViewModel(requireActivity() as AppCompatActivity)

        setRecyclerView()
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding?.rvUsers?.layoutManager = layoutManager

        authViewModel.getUsers().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)

                    val adapter = UserAdapter(result.data as ArrayList<User>)
                    adapter.setOnUserActionCallback(object : UserAdapter.UserActionCallback {
                        override fun onUpdate(user: User) {
                            val intent = Intent(requireActivity(), UserUpdateActivity::class.java)
                            intent.putExtra(USER_EXTRA, user)
                            startActivity(intent)
                        }

                        override fun onDelete(id: Int) {
                            showDeleteUserDialog(id)
                        }
                    })
                    binding?.rvUsers?.adapter = adapter
                }

                is Result.Error -> {
                    showLoading(false)
                    showToast(result.error)
                }
            }
        }
    }

    private fun showDeleteUserDialog(id: Int) {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle("Data User")
            setMessage("Ingin menghapus user dengan id $id?")
            setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            setPositiveButton("Ya") { _, _ ->
                deleteUser(id)
            }
        }.show()
    }

    private fun deleteUser(id: Int) {
        authViewModel.deleteUser(id).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}

                is Result.Success -> {
                    showToast("Berhasil menghapus user dengan id $id")
                }

                is Result.Error -> {}
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarUser?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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
        const val USER_EXTRA = "user_item_extra"
    }
}