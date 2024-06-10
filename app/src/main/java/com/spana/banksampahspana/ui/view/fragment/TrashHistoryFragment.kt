package com.spana.banksampahspana.ui.view.fragment

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
import com.spana.banksampahspana.data.remote.response.Trash
import com.spana.banksampahspana.databinding.FragmentTrashHistoryBinding
import com.spana.banksampahspana.ui.adapter.TrashAdapter
import com.spana.banksampahspana.ui.viewmodel.TrashViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory

class TrashHistoryFragment : Fragment() {

    private var _binding: FragmentTrashHistoryBinding? = null
    private val binding get() = _binding

    private lateinit var trashViewModel: TrashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrashHistoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trashViewModel = obtainTrashViewModel(requireContext() as AppCompatActivity)


        setRecyclerView()
    }

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding?.rvTrashes?.layoutManager = layoutManager

        trashViewModel.getUserTrash().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)

                    val adapter = TrashAdapter(result.data?.data as ArrayList<Trash>)
                    binding?.rvTrashes?.adapter = adapter
                }

                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarTrash?.visibility = if (isLoading) View.VISIBLE else View.GONE
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