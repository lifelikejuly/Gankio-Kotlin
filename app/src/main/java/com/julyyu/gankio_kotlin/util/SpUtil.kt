package com.julyyu.gankio_kotlin.util

import android.content.Context
import com.julyyu.gankio_kotlin.App
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by julyyu on 2017/9/4.
 */
class SpUtil<T>() : ReadWriteProperty<Any?, T> {

    var key: String? = null
    var value: T? = null

    constructor(name: String, default: T) : this() {
        key = name
        value = default
    }

    val prefs by lazy { App.context!!.getSharedPreferences("gank_kotlin", Context.MODE_PRIVATE) }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(key!!, value!!)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(key!!, value)
    }

    fun delete(vararg key: String): Unit {
        if (key.size == 0) {
            prefs.edit().clear().commit()
            return
        }
        for (i in 0..key.size) {
            prefs.edit().remove(key[i]).commit()
        }
    }

    private fun <U> findPreference(name: String, default: U): U = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> throw IllegalArgumentException("The data can not be saved")
        }
        res as U
    }

    private fun <U> putPreference(name: String, value: U) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("The data can not be saved")
        }.apply()
    }
}