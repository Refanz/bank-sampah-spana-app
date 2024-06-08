package com.spana.banksampahspana.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.spana.banksampahspana.data.repository.TrashCategoryRepository

class TrashViewModel(private val trashCategoryRepository: TrashCategoryRepository) : ViewModel() {
    fun getTrashCategory() = trashCategoryRepository.getTrashCategories()
}