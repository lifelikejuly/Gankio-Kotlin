package com.julyyu.gankio_kotlin.http

import com.julyyu.gankio_kotlin.model.Gank

/**
 * Created by JulyYu on 2017/4/24.
 */
class GankDateResponse{

    public var error: Boolean = false
    public var results: Results? = null
    public var category: List<String>? = null

    class Results{
        public var Android: List<Gank>? = null
        public var iOS: List<Gank>? = null
        public var 休息视频: List<Gank>? = null
        public var 拓展资源: List<Gank>? = null
        public var 福利: List<Gank>? = null
    }
}