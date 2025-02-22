package com.spana.banksampahspana.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spana.banksampahspana.data.di.Injection

class ViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(
                Injection.provideAuthRepository(mApplication),
                Injection.provideAuthPreferences(mApplication)
            ) as T
        }

        if (modelClass.isAssignableFrom(TrashViewModel::class.java)) {
            return TrashViewModel(
                Injection.provideTrashCategoryRepository(mApplication),
                Injection.provideTrashRepository(mApplication)
            ) as T
        }

        if (modelClass.isAssignableFrom(WithdrawalViewModel::class.java)) {
            return WithdrawalViewModel(
                Injection.provideWithdrawalRepository(mApplication)
            ) as T
        }

        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            return NotificationViewModel(
                Injection.provideNotificationRepository(mApplication)
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(mApplication: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(mApplication)
                }
            }

            return INSTANCE as ViewModelFactory
        }
    }
}