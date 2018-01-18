package com.julyyu.gankio_kotlin.util

import android.app.Activity
import android.content.Context
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker

/**
 * Created by julyyu on 2017/9/6.
 */
class PermissionUtil{

    fun checkingPermissionRegister(app: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(app, permission) == PermissionChecker.PERMISSION_GRANTED
    }


    fun checkingPermissionRegister(app: Context, vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (!checkingPermissionRegister(app, permission)) {
                return false
            }
        }
        return true
    }

    fun requestPermission(activity: Activity, requestCode: Int, vararg permissions: String) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    fun hasRejectPermission(grantResults: IntArray?): Boolean {
        for (grantResult in grantResults!!) {
            if (grantResult != PermissionChecker.PERMISSION_GRANTED) {
                return true
            }
        }
        return false
    }
}