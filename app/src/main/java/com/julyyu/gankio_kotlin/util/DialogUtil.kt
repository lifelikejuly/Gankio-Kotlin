package com.julyyu.gankio_kotlin.util

import android.app.Activity
import android.support.v7.app.AlertDialog
import com.julyyu.gankio_kotlin.Route

/**
 * Created by julyyu on 2017/9/6.
 */
class DialogUtil{

    fun getPermissionDialog(activity: Activity, packageName: String): AlertDialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("无法获取妹子,请打开应用外部存储权限")
        builder.setCancelable(false)
        builder.setTitle("权限被拒!")
        builder.setNegativeButton("取消") { dialog, which ->
            dialog.dismiss()
            activity.finish()
        }
        builder.setPositiveButton("确定") { dialog, which ->
            Route().appDefualtSetting(activity, packageName, 3)
        }
        return builder.create()
    }
}