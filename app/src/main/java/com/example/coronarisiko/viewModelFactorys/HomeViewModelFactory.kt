package com.example.coronarisiko.viewModelFactorys

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coronarisiko.viewmodels.HomeViewModel

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val application : Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(application) as T
    }
}