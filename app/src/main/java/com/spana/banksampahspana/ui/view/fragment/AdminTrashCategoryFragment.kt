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
import com.spana.banksampahspana.data.remote.response.TrashCategoryItem
import com.spana.banksampahspana.databinding.FragmentAdminTrashCategoryBinding
import com.spana.banksampahspana.ui.adapter.TrashCategoryAdminAdapter
import com.spana.banksampahspana.ui.view.activity.TrashCategoryUpdateActivity
import com.spana.banksampahspana.ui.viewmodel.TrashViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory

class AdminTrashCategoryFragment : Fragment() {

    private var _binding: FragmentAdminTrashCategoryBinding? = null
    private val binding get() = _binding

    private lateinit var trashViewModel: TrashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminTrashCategoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trashViewModel = obtainTrashCategory(requireActivity() as AppCompatActivity)

        setRecyclerView()
    }

    private fun setRecyclerView() {

        val layoutManager = LinearLayoutManager(requireContext())
        binding?.rvTrashCategories?.layoutManager = layoutManager

        trashViewModel.getTrashCategory().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)

                    val adapter =
                        TrashCategoryAdminAdapter(result.data as ArrayList<TrashCategoryItem>)
                    binding?.rvTrashCategories?.adapter = adapter

                    adapter.setTrashCategoryActionCallback(object :
                        TrashCategoryAdminAdapter.TrashCategoryActionCallback {
                        override fun onUpdate(trashCategoryItem: TrashCategoryItem) {
                            val intent =
                                Intent(requireContext(), TrashCategoryUpdateActivity::class.java)
                            intent.putExtra(TRASH_CATEGORY_ITEM_EXTRA, trashCategoryItem);
                            startActivity(intent)
                        }

                        override fun onDelete(id: Int) {
                            showDialogDeleteTrashCategory(id)
                        }
                    })
                }

                is Result.Error -> {
                    showLoading(false)
                }
            }
        }
    }

    private fun showDialogDeleteTrashCategory(id: Int) {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle("Kategori Sampah")
            setMessage("Ingin menghapus kategori sampah dengan id $id")
            setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            setPositiveButton("Ya") { _, _ ->
                trashViewModel.deleteTrashCategory(id).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> {}

                        is Result.Success -> {
                            showToast("Berhasil menghapus kategori sampah dengan id $id")
                        }

                        is Result.Error -> {
                            showToast(result.error)
                        }
                    }
                }
            }
        }.show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarTrash?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainTrashCategory(activity: AppCompatActivity): TrashViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[TrashViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TRASH_CATEGORY_ITEM_EXTRA = "trash_category_extra"
    }
}