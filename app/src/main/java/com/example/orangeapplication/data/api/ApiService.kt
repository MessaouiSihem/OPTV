package com.example.orangeapplication.data.api


import com.example.orangeapplication.data.model.detail.DetailProgram
import com.example.orangeapplication.data.model.program.Program
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("apps/v2/contents")
    fun getPrograms(
        @Query(
            value = "search",
            encoded = true
        ) program_title: String
    ): Observable<Program>

    @GET("{detailLink}")
    fun getProgramDetail(@Path("detailLink", encoded = true) detail: String): Observable<DetailProgram>
}