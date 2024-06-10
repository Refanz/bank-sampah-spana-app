package com.spana.banksampahspana.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.spana.banksampahspana.data.remote.response.Trash
import com.spana.banksampahspana.data.repository.TrashCategoryRepository
import com.spana.banksampahspana.data.repository.TrashRepository


class TrashViewModel(
    private val trashCategoryRepository: TrashCategoryRepository,
    private val trashRepository: TrashRepository
) : ViewModel() {
    fun getTrashCategory() = trashCategoryRepository.getTrashCategories()

    fun addNewTrash(trash: Trash) = trashRepository.addNewTrash(trash)

    fun getUserTrash() = trashRepository.getUserTrash()
}