package com.spana.banksampahspana.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.spana.banksampahspana.databinding.FragmentAdminHomeBinding
import com.spana.banksampahspana.ui.view.activity.TrashAddAdminActivity
import com.spana.banksampahspana.ui.view.activity.TrashCategoryAddActivity
import com.spana.banksampahspana.ui.viewmodel.AuthViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory

class AdminHomeFragment : Fragment() {

    private var _binding: FragmentAdminHomeBinding? = null
    private val binding get() = _binding

    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = obtainAuthViewModel(requireActivity() as AppCompatActivity)

        authViewModel.getAuthUser().observe(viewLifecycleOwner) { user ->
            binding?.txtAdmin?.text = "Hai, ${user.name}"
        }

        binding?.btnAddTrash?.setOnClickListener {
            val intent = Intent(requireActivity(), TrashAddAdminActivity::class.java)
            startActivity(intent)
        }

        binding?.btnAddTrashCategory?.setOnClickListener {
            val intent = Intent(requireActivity(), TrashCategoryAddActivity::class.java)
            startActivity(intent)
        }
    }

    private fun obtainAuthViewModel(activity: AppCompatActivity): AuthViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AuthViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}