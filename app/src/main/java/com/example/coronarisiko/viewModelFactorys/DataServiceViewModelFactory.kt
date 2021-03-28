package com.example.coronarisiko.viewModelFactorys

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coronarisiko.repository.Repository
import com.example.coronarisiko.viewmodels.DataServiceViewModel

@Suppress("UNCHECKED_CAST")
class DataServiceViewModelFactory(
    private val repository: Repository, private val application : Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DataServiceViewModel(repository, application) as T
    }
}