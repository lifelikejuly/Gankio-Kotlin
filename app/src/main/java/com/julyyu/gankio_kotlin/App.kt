package com.julyyu.gankio_kotlin

import android.app.Application
import android.content.Context

/**
 * Created by julyyu on 2017/5/1.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
//        FIR.init(this)
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
                .name("sees.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(configuration)
    }

    companion object {

        var context: Context ?= null
    }
}