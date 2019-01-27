package com.julyyu.gankio_kotlin.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

/**
 * Created by julyyu on 2017/5/1.
 */
object PackgeUtil {

    /**
     * 获取app版本号
     */
    fun getAppVersionCode(context: Context): Int {
        val pm = context.packageManager
        val pi: PackageInfo
        try {
            pi = pm.getPackageInfo(context.packageName, 0)
            return pi.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return 0
    }

    /**
     * 获取app版本号
     */
    fun getAppVersionName(context: Context): String {
        val pm = context.packageManager
        val pi: PackageInfo
        try {
            pi = pm.getPackageInfo(context.packageName, 0)
            return pi.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * 获取appName
     */
    fun getAppName(context: Context): String {
        val pm = context.packageManager
        val pi: PackageInfo
        try {
            pi = pm.getPackageInfo(context.packageName, 0)
            return pi.applicationInfo.loadLabel(context.packageManager).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return ""
    }
}
