package com.spana.banksampahspana.ui.view.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.spana.banksampahspana.data.Result
import com.spana.banksampahspana.data.remote.response.Trash
import com.spana.banksampahspana.databinding.ActivityTrashAddBinding
import com.spana.banksampahspana.ui.viewmodel.AuthViewModel
import com.spana.banksampahspana.ui.viewmodel.TrashViewModel
import com.spana.banksampahspana.ui.viewmodel.ViewModelFactory
import com.spana.banksampahspana.util.Utils.toEditable

class TrashAddActivity : AppCompatActivity() {

    private var _binding: ActivityTrashAddBinding? = null
    private val binding get() = _binding

    private lateinit var trashViewModel: TrashViewModel

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        trashViewModel = obtainTrashViewModel(this)
        authViewModel = obtainAuthViewModel(this)

        initTrashCategoryComboBox()

        binding?.topAppBar?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setTrashCategoryChangeListener()

        setWeightChangeListener()

        binding?.btnAddTrash?.setOnClickListener {
            addNewTrash()
        }

    }

    private fun initBinding() {
        _binding = ActivityTrashAddBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)
    }

    private fun addNewTrash() {
        val trashType = binding?.inputTrashCategory?.text?.split("/")?.first().toString()
        val weight = binding?.inputWeight?.text.toString().toDouble()
        val totalDeposit = binding?.txtTotalPriceVal?.text?.split(".")?.last()?.toInt() ?: 0

        authViewModel.getAuthUser().observe(this) { user ->

            val trash = Trash(
                totalDeposit, trashType, weight, user.id, ""
            )

            trashViewModel.addNewTrash(trash).observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)
                        showToast("Berhasil menambahkan sampah")
                    }

                    is Result.Error -> {
                        showLoading(false)
                        showToast(result.error)
                    }
                }
            }
        }
    }

    private fun setTrashCategoryChangeListener() {
        binding?.inputTrashCategory?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val price = s?.split(" ")?.last().toString()
                binding?.inputTrashPrice?.text = price.toEditable()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun setWeightChangeListener() {
        binding?.inputWeight?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val inputPrice = binding?.inputTrashPrice?.text.toString()
                val inputWeight = s.toString()

                binding?.txtTotalPriceVal?.text =
                    "Rp.${calculateTotalPrice(inputPrice, inputWeight)}"
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun initTrashCategoryComboBox() {
        trashViewModel.getTrashCategory().observe(this) { result ->
            if (result is Result.Success) {
                val trashCategories = result.data

                val trashCategoryList = ArrayList<String>()

                trashCategories?.forEach { item ->
                    trashCategoryList.add("${item?.name}/${item?.unit} - ${item?.price}")
                }

                (binding?.inputLayoutTrashCategory?.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(
                    trashCategoryList.toTypedArray()
                )
            }
        }
    }

    private fun calculateTotalPrice(inputPrice: String, inputWeight: String): Int {
        val price = if (inputPrice.isNotEmpty()) {
            inputPrice.toInt()
        } else {
            0
        }

        val weight = if (inputWeight.isNotEmpty()) {
            inputWeight.toInt()
        } else {
            0
        }

        return weight * price
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarAddTrash?.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainTrashViewModel(activity: AppCompatActivity): TrashViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[TrashViewModel::class.java]
    }

    private fun obtainAuthViewModel(activity: AppCompatActivity): AuthViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AuthViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}