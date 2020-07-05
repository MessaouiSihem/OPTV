package com.example.orangeapplication.data.repository

import android.util.Log
import com.example.orangeapplication.data.api.ApiService
import com.example.orangeapplication.data.model.Program
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Level

class ProgramRepository {

    private val url: String = "https://api.ocs.fr/"
    // Customize Logging message
    private var logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.e("REQUEST", message) })


    private fun createRepository(): ApiService {
        // configure logging request
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client =  OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }

    fun getProgramList(title: String): Observable<Program> {
        return  createRepository().getPrograms(title)
    }
}