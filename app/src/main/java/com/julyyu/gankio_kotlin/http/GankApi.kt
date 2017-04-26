package com.julyyu.gankio_kotlin.http

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by JulyYu on 2017/4/24.
 */
interface GankApi{
    @GET("data/{Classify}/{Count}/{Page}")
    abstract fun getGankIoData(@Path("Classify") classify: String, @Path("Count") count: Int, @Path("Page") page: Int): Call<GankResponse>

    @GET("day/{date}")
    fun getGankDayDate(@Path("date") date: String): Observable<GankDateResponse>

    @GET("day/history")
    fun getGankHistory(): Observable<GankHistoryResponse>
}