package com.julyyu.gankio_kotlin.ui

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import butterknife.bindView
import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.util.PackgeUtil
import com.julyyu.gankio_kotlin.util.UpdateUtil

class AboutActivity : AppCompatActivity() {

    val appBarLayout: AppBarLayout by bindView(R.id.appbarlayout)
    val toolbar: Toolbar by bindView(R.id.toolbar)
    val versionName : TextView by bindView(R.id.tv_app_version)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_close)
        versionName.setText(PackgeUtil.getAppVersionName(this))
    }

    fun onclick(view: View) {
        when (view.id) {
            R.id.btn_home -> {
                val uri = Uri.parse("https://github.com/yuhaocan/gankio-kotlin")
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.data = uri
                startActivity(intent)
            }
            R.id.btn_update -> {
                UpdateUtil().getcheckUpdate(this@AboutActivity)
            }
        }
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
}
