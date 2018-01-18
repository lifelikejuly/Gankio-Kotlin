package com.julyyu.gankio_kotlin.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.SwitchCompat
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import kotterknife.bindView

import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.util.DataClaenManager
import com.julyyu.gankio_kotlin.util.SpUtil
import java.io.File

/**
 * 设置页面
 */
class SettingActivity : AppCompatActivity() {

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val cbAndroid: SwitchCompat by bindView(R.id.sw_android)
    val cbiOS: SwitchCompat by bindView(R.id.sw_ios)
    val cbMeizi: SwitchCompat by bindView(R.id.sw_meizi)
    val cbFront: SwitchCompat by bindView(R.id.sw_front_end)
    val cbVideo: SwitchCompat by bindView(R.id.sw_video)
    val cbOther: SwitchCompat by bindView(R.id.sw_res)
    val cbArray = arrayOfNulls<SwitchCompat>(6)

    var androids : Boolean by SpUtil("android",true)
    var ios     : Boolean by SpUtil("ios",true)
    var front   : Boolean by SpUtil("front",false)
    var meizi   : Boolean by SpUtil("meizi",true)
    var video   : Boolean by SpUtil("video",false)
    var other   : Boolean by SpUtil("other",false)

//    val tvCache: TextView by bindView(R.id.tv_cache)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_close)
        toolbar.setTitle("设置")
//        tvCache.text = DataClaenManager().getCacheSize(externalCacheDir)


        cbAndroid.isChecked = androids
        cbiOS.isChecked = ios
        cbMeizi.isChecked = meizi
        cbFront.isChecked = front
        cbVideo.isChecked = video
        cbOther.isChecked = other
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                androids = cbAndroid.isChecked
                ios = cbiOS.isChecked
                meizi = cbMeizi.isChecked
                front = cbFront.isChecked
                video = cbVideo.isChecked
                other = cbOther.isChecked
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    fun clearCache(view: View) {
//        DataClaenManager().cleanExternalCache(this@SettingActivity)
//    }
//
//    fun setTheme(view : View){
//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        else
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        recreate()
//    }
}
