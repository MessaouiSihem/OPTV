package com.example.orangeapplication.data.repository

import com.example.orangeapplication.data.api.ApiService
import com.example.orangeapplication.data.model.detail.DetailProgram
import com.example.orangeapplication.data.model.program.Program
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ProgramRepository {

    companion object {
        const val MAIN_URL = "https://api.ocs.fr/"
    }


    private fun createRepository(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(MAIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    fun getProgramList(title: String): Observable<Program> {
        return createRepository().getPrograms(title)
    }

    fun getProgramDetail(link: String): Observable<DetailProgram> {
        return createRepository().getProgramDetail(link)
    }
}