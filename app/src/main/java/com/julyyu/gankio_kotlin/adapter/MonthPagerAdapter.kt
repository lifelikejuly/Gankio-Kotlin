package com.julyyu.gankio_kotlin.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import com.julyyu.gankio_kotlin.ui.calendar.MonthFragment
import org.joda.time.DateTime

/**
 * Created by JulyYu on 2017/5/24.
 */
class MonthPagerAdapter : FragmentStatePagerAdapter {

    var SIZE = 10
    var nowyear: Int? = null
    var nowmonth: Int? = null

    constructor(fm: FragmentManager) : super(fm) {
        val dt = DateTime()
        nowyear = dt.year
        nowmonth = dt.monthOfYear
    }

    constructor(fm: FragmentManager, size: Int) : super(fm) {
        SIZE = size
        val dt = DateTime()
        nowyear = dt.year
        nowmonth = dt.monthOfYear
    }

//    override fun getItem(position: Int): Fragment {
//        // getItem is called to instantiate the fragment for the given page.
//        // Return a PlaceholderFragment (defined as a static inner class below).
//        val month = getMonthByPosition(position)
//        val year = getYearByPosition(position)
//        val fragment = MonthFragment()
//        val args = Bundle()
//        args.putInt(MonthFragment.ARG_SECTION_YEAR, year)
//        args.putInt(MonthFragment.ARG_SECTION_MONTH, month)
//        fragment.arguments = args
//        return fragment
////        return MonthFragment.newInstance(year, month)
//    }

    override fun getItem(position: Int) = MonthFragment.newInstance(getYearByPosition(position), getMonthByPosition(position))

    override fun getCount(): Int {
        // 10 years 120 months
        return SIZE
    }

//    fun getMonthByPosition(position: Int): Int {
//        var month = (position + 1) % 12
//        if ((position + 1) % 12 == 0) {
//            month = 12
//        }
//        return month
//    }
//
//    fun getYearByPosition(position: Int): Int {
//        var year = mStartYear + (position + 1) / 12
//        if (getMonthByPosition(position) == 12) {
//            year -= 1
//        }
//        return year
//    }

    fun getMonthByPosition(position: Int): Int {
        var month = nowmonth!! - (SIZE - position - 1)
        month = if (month > 0) month
        else
            if (month == 0) 12
            else (12 - Math.abs(month) % 12)
        return month
    }

    fun getYearByPosition(position: Int): Int {
        var month = nowmonth!! - (SIZE - position - 1)
        var year: Int? = null
        if (month === 0)
            year = nowyear!!.minus(1)
        else
            if (month < 0) {
                val years = Math.abs(month) / 12
                val months = Math.abs(month) % 12
                year = nowyear!! - years - (if (months > 0) 1 else 0) - if (years > 0 && months == 0) 1 else 0
            } else
                year = nowyear
        return year as Int
    }

    fun getYearMonth(position: Int): String {
        return getYearByPosition(position).toString() + "-" + getMonthByPosition(position)
    }
}