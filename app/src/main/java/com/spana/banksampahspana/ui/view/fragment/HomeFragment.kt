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
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.remote.response.TrashCategoryItem
import com.spana.banksampahspana.databinding.FragmentHomeBinding
import com.spana.banksampahspana.ui.adapter.TrashCategoryAdapter
import com.spana.banksampahspana.ui.view.activity.TrashAddActivity
import com.spana.banksampahspana.ui.viewmodel.AuthViewModel
import com.spana.banksampahspana.ui.viewmodel.TrashViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private lateinit var authViewModel: AuthViewModel
    private lateinit var trashViewModel: TrashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel = obtainViewModel(requireContext() as AppCompatActivity)
        trashViewModel = obtainTrashViewModel(requireContext() as AppCompatActivity)

        binding?.btnAddTrash?.setOnClickListener {
            val intent = Intent(requireContext(), TrashAddActivity::class.java)
            startActivity(intent)
        }

        authViewModel.getAuthUser().observe(viewLifecycleOwner) { user ->
            binding?.txtUser?.text = "Hai, ${user.name}"
        }

        initRecyclerView()

        trashViewModel.getTrashCategory().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}

                is Result.Success -> {
                    setRecyclerViewData(result.data as ArrayList<TrashCategoryItem>)
                }

                is Result.Error -> {
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initRecyclerView() {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding?.rvTrashCategory?.layoutManager = layoutManager
    }

    private fun setRecyclerViewData(trashCategoryList: ArrayList<TrashCategoryItem>) {
        val adapter = TrashCategoryAdapter(trashCategoryList)
        binding?.rvTrashCategory?.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): AuthViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AuthViewModel::class.java]
    }

    private fun obtainTrashViewModel(activity: AppCompatActivity): TrashViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[TrashViewModel::class.java]
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}