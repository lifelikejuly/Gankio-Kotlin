package com.julyyu.gankio_kotlin.http

import com.julyyu.gankio_kotlin.model.Gank
import com.julyyu.gankio_kotlin.model.GankCollection

/**
 * Created by JulyYu on 2017/4/24.
 */
class GankDateResponse{

    public var error: Boolean = false
    public var results: GankCollection? = null
    public var category: List<String>? = null

}