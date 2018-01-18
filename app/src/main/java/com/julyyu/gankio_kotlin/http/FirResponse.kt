package com.julyyu.gankio_kotlin.http

/**
 * Created by julyyu on 2017/9/6.
 */
class FirResponse{

    var name: String? = null
    var version: String? = null
    var changelog: String? = null
    var versionShort: String? = null
    var build: String? = null
    var installUrl: String? = null
    var install_url: String? = null
    var update_url: String? = null
    var binary: BinaryBean? = null

    class BinaryBean {
        /**
         * fsize : 6446245
         */

        var fsize: Int = 0
    }
}