package com.julyyu.gankio_kotlin

import android.app.Application
import android.content.Context
import im.fir.sdk.FIR

/**
 * Created by julyyu on 2017/5/1.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        FIR.init(this)
    }

    companion object {

        var context: Context ?= null
    }
}