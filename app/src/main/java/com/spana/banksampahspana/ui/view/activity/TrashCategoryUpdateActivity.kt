package com.spana.banksampahspana.ui.view.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.remote.response.TrashCategoryItem
import com.spana.banksampahspana.databinding.ActivityTrashCategoryUpdateBinding
import com.spana.banksampahspana.ui.view.fragment.AdminTrashCategoryFragment
import com.spana.banksampahspana.ui.viewmodel.TrashViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory
import com.spana.banksampahspana.util.Utils.toEditable

class TrashCategoryUpdateActivity : AppCompatActivity() {

    private var _binding: ActivityTrashCategoryUpdateBinding? = null
    private val binding get() = _binding

    private lateinit var trashViewModel: TrashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        trashViewModel = obtainTrashViewModel(this)

        binding?.topAppBar?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding?.inputTrashCategory?.text = getIntentData().name?.toEditable()
        binding?.inputTrashCategoryPrice?.text = getIntentData().price.toString().toEditable()

        binding?.btnUpdateTrash?.setOnClickListener {
            updateTrashCategory()
        }

        showToast(getIntentData().id.toString())
    }

    private fun initBinding() {
        _binding = ActivityTrashCategoryUpdateBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    private fun getIntentData(): TrashCategoryItem {
        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(
                AdminTrashCategoryFragment.TRASH_CATEGORY_ITEM_EXTRA,
                TrashCategoryItem::class.java
            )
        } else {
            intent.getParcelableExtra(
                AdminTrashCategoryFragment.TRASH_CATEGORY_ITEM_EXTRA,
            )
        }

        if (data != null) {
            return data;
        }

        return TrashCategoryItem()
    }

    private fun updateTrashCategory() {

        val name = binding?.inputTrashCategory?.text
        val price = binding?.inputTrashCategoryPrice?.text

        val trashCategoryUpdate = TrashCategoryItem(
            id = getIntentData().id,
            name = name.toString(),
            price = price.toString().toInt()
        )

        trashViewModel.updateTrashCategory(trashCategoryUpdate).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    showToast("Berhasil ubah kategori sampah")
                }

                is Result.Error -> {
                    showLoading(false)
                    showToast(result.error)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarUpdateTrashCategory?.visibility =
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