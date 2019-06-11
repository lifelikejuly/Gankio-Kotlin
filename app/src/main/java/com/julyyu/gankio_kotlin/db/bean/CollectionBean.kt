package com.julyyu.gankio.db.bean

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by JulyYu on 2017/2/10.
 */

class CollectionBean : RealmObject() {

    var id: Int = 0
    var url: String? = null
    var des: String? = null
    var tag: String? = null
}
