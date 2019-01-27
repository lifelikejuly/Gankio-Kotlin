package com.julyyu.gankio_kotlin.ui.calendar

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.julyyu.gankio_kotlin.AppConst
import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.adapter.MonthPagerAdapter
import com.julyyu.gankio_kotlin.util.CalendarUtil
import kotlinx.android.synthetic.main.activity_calendar.*

class CalendarActivity : AppCompatActivity() , ViewPager.OnPageChangeListener {



//    val toolbar: Toolbar by bindView(R.id.toolbar)
//    val viewPager : ViewPager by bindView(R.id.viewpager)
    var monthAdapter : MonthPagerAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        toolbar.setNavigationIcon(R.drawable.ic_close)
        val months = CalendarUtil().MonthCount(AppConst.dates!!.last,AppConst.dates!!.first)
        monthAdapter = MonthPagerAdapter(supportFragmentManager,months)
        viewpager.addOnPageChangeListener(this@CalendarActivity)
        viewpager.adapter = monthAdapter
        viewpager.setCurrentItem(months - 1)
        supportActionBar!!.title = monthAdapter?.getYearMonth(viewpager.currentItem)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        toolbar.title = monthAdapter?.getYearMonth(position)
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
