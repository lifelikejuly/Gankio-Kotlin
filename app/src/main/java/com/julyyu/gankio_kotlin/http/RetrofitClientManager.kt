package com.julyyu.gankio_kotlin.http

import com.julyyu.gankio_kotlin.AppConst
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.fastjson.FastJsonConverterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * Created by JulyYu on 2017/4/24.
 */
class RetrofitClientManager {
    private var instance: RetrofitClientManager? = null
    private var retrofit: Retrofit? = null

    constructor(){
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        val okHttpClient = httpClient.addInterceptor(logging)
                .addInterceptor { chain ->
                    chain.proceed(chain.request())
                            .newBuilder()
                            .addHeader("Accept", "text/html,application/xhtml+xml,application/json,application/xml,*/*;q=0.8")
                            .addHeader("Cache-Control", "max-age=0")
                            .addHeader("Accept-Encoding", "gzip,deflate,sdch")
                            .addHeader("Connection", "close")
                            .addHeader("Content-Type", "application/json; charset=utf-8")
                            .build()
                }
                .build()
        retrofit = Retrofit.Builder()
                .baseUrl(AppConst.GANK_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    fun getInstance() : RetrofitClientManager{
        if (instance == null) {
            instance = RetrofitClientManager()
        }
        return instance as RetrofitClientManager
    }

    fun <T> createApi(service: Class<T>) : T{
         return retrofit!!.create(service)
    }
}