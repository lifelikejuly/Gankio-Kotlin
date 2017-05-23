package com.julyyu.gankio_kotlin.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import butterknife.bindView

import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.util.DataClaenManager
import java.io.File

class SettingActivity : AppCompatActivity() {

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val tvCache : TextView by bindView(R.id.tv_cache)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_close)
        toolbar.setTitle("设置")

        tvCache.text = DataClaenManager().getCacheSize(externalCacheDir)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun clearCache(view : View){
        DataClaenManager().cleanExternalCache(this@SettingActivity)
    }
}
