package com.julyyu.gankio_kotlin.http

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by julyyu on 2017/9/6.
 */
interface FirApi {

    @GET("http://api.fir.im/apps/latest/5907df60ca87a85eb7000084?api_token=91ae1ee9710ec563ab23cfec6d51b800")
    abstract fun getAppVersion(): Observable<FirResponse>

}