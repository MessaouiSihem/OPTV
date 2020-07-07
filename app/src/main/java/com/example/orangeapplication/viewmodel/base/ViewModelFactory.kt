package com.example.orangeapplication.viewmodel.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.orangeapplication.data.repository.ProgramRepository
import com.example.orangeapplication.viewmodel.DetailProgramViewModel
import com.example.orangeapplication.viewmodel.PlayerViewModel
import com.example.orangeapplication.viewmodel.ProgramViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ProgramViewModel::class.java) -> {
                return ProgramViewModel(
                    ProgramRepository()
                ) as T
            }
            modelClass.isAssignableFrom(DetailProgramViewModel::class.java) -> {
                return DetailProgramViewModel(
                    ProgramRepository()
                ) as T
            }
            modelClass.isAssignableFrom(PlayerViewModel::class.java) -> {
                return PlayerViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown class name")
        }
    }

}