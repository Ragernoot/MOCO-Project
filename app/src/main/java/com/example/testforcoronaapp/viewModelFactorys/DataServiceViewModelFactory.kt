package com.example.testforcoronaapp.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testforcoronaapp.model.room.AppDatabase
import com.example.testforcoronaapp.repository.Repository
import com.example.testforcoronaapp.viewmodels.DataServiceViewModel

@Suppress("UNCHECKED_CAST")
class DataServiceViewModelFactory(
    private val repository: Repository, private val database: AppDatabase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DataServiceViewModel(repository, database) as T
    }
}