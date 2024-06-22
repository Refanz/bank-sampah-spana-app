package com.spana.banksampahspana.ui.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.databinding.ActivityTrashCategoryAddBinding
import com.spana.banksampahspana.ui.viewmodel.TrashViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory

class TrashCategoryAddActivity : AppCompatActivity() {

    private var _binding: ActivityTrashCategoryAddBinding? = null
    private val binding get() = _binding

    private lateinit var trashViewModel: TrashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        trashViewModel = obtainTrashViewModel(this)

        binding?.topAppBar?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding?.btnAddTrash?.setOnClickListener {
            addTrashCategory()
        }
    }

    private fun initBinding() {
        _binding = ActivityTrashCategoryAddBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)
    }

    private fun addTrashCategory() {
        val inputName = binding?.inputTrashCategory?.text
        val inputPrice = binding?.inputTrashCategoryPrice?.text
        var prince = 0

        prince = if (inputPrice?.isEmpty() == true) {
            0
        } else {
            inputPrice.toString().toInt()
        }

        trashViewModel.addTrashCategory(inputName.toString(), prince).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    showToast("Berhasil menambahkan kategori sampah ${result.data?.name}")
                }

                is Result.Error -> {
                    showLoading(false)
                    showToast(result.error)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarAddTrashCategory?.visibility =
            if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainTrashViewModel(activity: AppCompatActivity): TrashViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[TrashViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}