package com.julyyu.gankio.api

import com.julyyu.gankio.bean.JiandanResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by julyyu on 2017/4/23 0023.
 */

interface JiandanApi {

    @GET("http://i.jandan.net/?oxwlxojflwblxbsapi=jandan.get_ooxx_comments")
    fun getJiandanXXOO(@Query("page") page: Int): Call<JiandanResponse>
}
