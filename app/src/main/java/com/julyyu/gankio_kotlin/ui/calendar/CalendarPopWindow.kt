package com.julyyu.gankio_kotlin.ui.calendar

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.adapter.MonthPagerAdapter
import kotlinx.android.synthetic.main.view_calendar.view.*

/**
 * Created by JulyYu on 2017/5/24.
 */
class CalendarPopWindow : PopupWindow {

    var context : Context
    var rootView : View


    constructor(context: AppCompatActivity){
        this.context = context
        rootView = LayoutInflater.from(context).inflate(R.layout.view_calendar,null)
        rootView.viewpager.adapter = MonthPagerAdapter(context.supportFragmentManager)
        setWindow()
    }

    protected fun setWindow() {
        this.contentView = rootView
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        this.height = ViewGroup.LayoutParams.MATCH_PARENT
        this.isFocusable = true
        val dw = ColorDrawable(0x88000000.toInt())
        this.setBackgroundDrawable(dw)
    }
}