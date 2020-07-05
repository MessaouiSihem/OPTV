package com.example.orangeapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.example.orangeapplication.data.model.Program
import com.example.orangeapplication.data.repository.ProgramRepository
import com.example.orangeapplication.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class ProgramViewModel(private val repository: ProgramRepository) : ViewModel() {

    private var programs = MutableLiveData<Resource<Program>>()
    private val compositeDisposable = CompositeDisposable()

    fun fetchPrograms(search: String) {
        programs.postValue(Resource.loading(null))
        compositeDisposable.add(
            repository.getProgramList(search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userList ->
                    programs.postValue(Resource.success(userList))
                }, { throwable ->
                    programs.postValue(
                        Resource.error(
                            "Une erreur est survenue ! ${throwable.message}",
                            null
                        )
                    )
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getPrograms(): LiveData<Resource<Program>> {
        return programs
    }
}