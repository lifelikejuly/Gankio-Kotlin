package com.julyyu.gankio_kotlin.model

/**
 * Created by julyyu on 2017/5/1.
 */
class UpdateVersion{

    var name: String? = null
    var version: String? = null
    var changelog: String? = null
    var versionShort: String? = null
    var build: String? = null
    var installUrl: String? = null
    var install_url: String? = null
    var update_url: String? = null
    /**
     * fsize : 6446245
     */

    var binary: BinaryBean? = null


    class BinaryBean {
        var fsize: Int = 0
    }
}