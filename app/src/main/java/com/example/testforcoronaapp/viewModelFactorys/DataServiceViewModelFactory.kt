package com.example.testforcoronaapp.viewModelFactorys

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testforcoronaapp.repository.Repository
import com.example.testforcoronaapp.viewmodels.DataServiceViewModel

@Suppress("UNCHECKED_CAST")
class DataServiceViewModelFactory(
    private val repository: Repository, private val application : Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DataServiceViewModel(repository, application) as T
    }
}