package com.spana.banksampahspana.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.spana.banksampahspana.data.repository.NotificationRepository

class NotificationViewModel(private val notificationRepository: NotificationRepository) :
    ViewModel() {

    fun sendNotification(id: Int, title: String, body: String) =
        notificationRepository.sendNotification(id, title, body)

    fun updateToken(id: Int, token: String) = notificationRepository.updateToken(id, token)
}