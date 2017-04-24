package com.julyyu.gankio_kotlin.model

import java.util.*

/**
 * Created by JulyYu on 2017/4/24.
 */
class Gank{
    /**
     * _id : 586528c7421aa94dbe2ccdae
     * createdAt : 2016-12-29T23:16:23.876Z
     * desc : H5唤起原生应用
     * publishedAt : 2016-12-30T16:16:11.125Z
     * source : web
     * type : Android
     * url : http://ihongqiqu.com/2015/12/03/html-call-native-app/
     * used : true
     * who : Jin
     */

    var _id: String? = null
    var createdAt: String? = null
    var desc: String? = null
    var images: List<String>? = null
    var publishedAt: Date? = null
    var source: String? = null
    var type: String? = null
    var url: String? = null
    var used: Boolean = false
    var who: String? = null
}
