package com.example.orangeapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.orangeapplication.data.model.detail.DetailProgram
import com.example.orangeapplication.data.repository.ProgramRepository
import com.example.orangeapplication.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailProgramViewModel(
    private val repository: ProgramRepository
) : ViewModel() {

    private var detailProgram = MutableLiveData<Resource<DetailProgram>>()
    private val compositeDisposable = CompositeDisposable()


    fun fetchDetail(link: String) {
        detailProgram.postValue(Resource.loading(null))
        compositeDisposable.add(
            repository.getProgramDetail(link)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ detail ->
                    detailProgram.postValue(Resource.success(detail))
                }, { throwable ->
                    detailProgram.postValue(
                        Resource.error(
                            "Une erreur est survenue !",
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

    fun getDetail(): LiveData<Resource<DetailProgram>> {
        return detailProgram
    }
}