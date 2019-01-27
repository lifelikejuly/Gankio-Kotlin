package com.julyyu.gankio_kotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.julyyu.gankio_kotlin.model.Girl
import com.julyyu.gankio_kotlin.ui.AboutActivity
import com.julyyu.gankio_kotlin.ui.GirlsActivity
import com.julyyu.gankio_kotlin.ui.SettingActivity
import com.julyyu.gankio_kotlin.ui.calendar.CalendarActivity
import java.util.*

/**
 * Created by julyyu on 2017/4/28.
 * 路由
 */
class Route{

    fun visitGirls(context : Context,girls : ArrayList<Girl>,position : Int){
        val intent = Intent(context,GirlsActivity::class.java)
        intent.putExtra("position",position)
        intent.putParcelableArrayListExtra("girls",girls)
        context.startActivity(intent)
    }

    fun about(context : Context){
        val intent = Intent(context,AboutActivity::class.java)
        context.startActivity(intent)
    }

    fun setting(context: Context){
        val intent = Intent(context,SettingActivity::class.java)
        context.startActivity(intent)
    }

    fun Calendar(context: Context){
        val intent = Intent(context, CalendarActivity::class.java)
        context.startActivity(intent)
    }

    fun appDefualtSetting(activity: Activity, packagename: String, result: Int) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + packagename)
        activity.startActivityForResult(intent, result)
    }
}