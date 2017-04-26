package com.julyyu.gankio_kotlin.http

/**
 * Created by JulyYu on 2017/4/24.
 */
object ApiFactory{

    private var gankApi: GankApi? = null

    fun getGankApi() : GankApi{
        if (gankApi == null) {
            gankApi = RetrofitClientManager().getInstance().createApi(GankApi::class.java)
        }
        return gankApi as GankApi
    } 
}