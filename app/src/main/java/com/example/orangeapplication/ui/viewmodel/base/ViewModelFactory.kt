package com.example.orangeapplication.ui.viewmodel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.orangeapplication.data.repository.ProgramRepository
import com.example.orangeapplication.ui.viewmodel.ProgramViewModel

class ViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgramViewModel::class.java)) {
            return ProgramViewModel(ProgramRepository()) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}