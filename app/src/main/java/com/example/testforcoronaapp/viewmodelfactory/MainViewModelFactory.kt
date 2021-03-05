package com.example.testforcoronaapp.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testforcoronaapp.repository.Repository
import com.example.testforcoronaapp.viewmodels.MainViewModel

class MainViewModelFactory(
        private val repository: Repository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }

}