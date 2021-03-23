package com.example.testforcoronaapp.viewModelFactorys

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testforcoronaapp.model.room.AppDatabase
import com.example.testforcoronaapp.viewmodels.HomeViewModel

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val database: AppDatabase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(database) as T
    }
}