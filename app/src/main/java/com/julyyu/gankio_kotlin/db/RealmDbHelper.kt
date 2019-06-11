package com.julyyu.gankio.db

import com.julyyu.gankio.App

import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by JulyYu on 2017/2/10.
 */

class RealmDbHelper {
    init {
        Realm.init(App.getContext())
        val configuration = RealmConfiguration.Builder()
                .name("gankio.realm")
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(configuration)
    }

    companion object {

        internal var realmDbHelper: RealmDbHelper? = null

        val instance: RealmDbHelper
            get() {
                if (realmDbHelper == null) {
                    realmDbHelper = RealmDbHelper()
                }
                return realmDbHelper
            }
    }


}
