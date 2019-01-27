package com.julyyu.gankio_kotlin.http

import com.julyyu.gankio_kotlin.model.Gank
import java.util.*

/**
 * Created by JulyYu on 2017/4/24.
 */
class GankResponse{
    var error: Boolean = false
    var results: ArrayList<Gank>? = null
}