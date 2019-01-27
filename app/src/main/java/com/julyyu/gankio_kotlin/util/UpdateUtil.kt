package com.julyyu.gankio_kotlin.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.support.v7.view.ContextThemeWrapper
import android.text.TextUtils
import android.widget.Toast
import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.http.ApiFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by julyyu on 2017/5/1.
 */
class UpdateUtil{

    fun checkUpdate(context: Context) {
        ApiFactory.getFirApi()
                .getAppVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val versionName = PackgeUtil.getAppVersionName(context)
                    val version = PackgeUtil.getAppVersionCode(context)
                    if (!TextUtils.equals(it!!.version, version.toString() + "") || !TextUtils.equals(it!!.versionShort, versionName)) {
                        val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AppTheme))
                        builder.setCancelable(false)
                        builder.setTitle("发现新版本")
                        builder.setMessage(context.getString(R.string.version_content,
                                it!!.versionShort,
                                it!!.changelog))
                        builder.setNegativeButton("取消", null)
                        builder.setPositiveButton("下载") { dialog, which ->
                            val uri = Uri.parse(it!!.install_url)
                            val intent = Intent()
                            intent.action = Intent.ACTION_VIEW
                            intent.data = uri
                            context.startActivity(intent)
                        }
                        builder.show()
                    }

                }
    }

    fun getcheckUpdate(context: Context) {

        ApiFactory.getFirApi()
                .getAppVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val versionName = PackgeUtil.getAppVersionName(context)
                    val version = PackgeUtil.getAppVersionCode(context)
                    if (!TextUtils.equals(it!!.version, version.toString() + "") || !TextUtils.equals(it!!.versionShort, versionName)) {
                        val builder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AppTheme))
                        builder.setCancelable(false)
                        builder.setTitle("发现新版本")
                        builder.setMessage(context.getString(R.string.version_content,
                                it!!.versionShort,
                                it!!.changelog))
                        builder.setNegativeButton("取消", null)
                        builder.setPositiveButton("下载") { dialog, which ->
                            val uri = Uri.parse(it!!.install_url)
                            val intent = Intent()
                            intent.action = Intent.ACTION_VIEW
                            intent.data = uri
                            context.startActivity(intent)
                        }
                        builder.show()
                    }else{
                        Toast.makeText(context,"已经是最新版本",Toast.LENGTH_SHORT).show();
                    }
                }

    }
}