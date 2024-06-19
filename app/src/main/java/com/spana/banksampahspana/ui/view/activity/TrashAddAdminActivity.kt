package com.spana.banksampahspana.ui.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.spana.banksampahspana.databinding.ActivityTrashAddAdminBinding

class TrashAddAdminActivity : AppCompatActivity() {

    private var _binding: ActivityTrashAddAdminBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        binding?.topAppBar?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun initBinding() {
        _binding = ActivityTrashAddAdminBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}