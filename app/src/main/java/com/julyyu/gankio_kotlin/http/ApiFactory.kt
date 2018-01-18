package com.julyyu.gankio_kotlin.http

/**
 * Created by JulyYu on 2017/4/24.
 */
object ApiFactory{

    private var gankApi: GankApi? = null
    private var firApi: FirApi? = null

    fun getGankApi() : GankApi{
        if (gankApi == null) {
            gankApi = RetrofitClientManager().getInstance().createApi(GankApi::class.java)
        }
        return gankApi as GankApi
    }

    fun getFirApi() : FirApi{
        if (firApi == null) {
            firApi = RetrofitClientManager().getInstance().createApi(FirApi::class.java)
        }
        return firApi as FirApi
    }
}