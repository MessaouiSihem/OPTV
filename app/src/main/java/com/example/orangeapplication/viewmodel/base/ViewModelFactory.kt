package com.example.orangeapplication.viewmodel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.orangeapplication.data.repository.ProgramRepository
import com.example.orangeapplication.viewmodel.ProgramViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgramViewModel::class.java)) {
            return ProgramViewModel(
                ProgramRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}