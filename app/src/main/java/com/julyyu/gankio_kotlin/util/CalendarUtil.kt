package com.julyyu.gankio_kotlin.util

import org.joda.time.DateTime
import java.text.SimpleDateFormat

/**
 * Created by JulyYu on 2017/5/24.
 */
class CalendarUtil{

    fun MonthDays(isLeapYear: Boolean, month: Int): Int {
        var mDays = 0
        when (month) {
            1, 3, 5, 7, 8, 10, 12 -> mDays = 31
            4, 6, 9, 11 -> mDays = 30
            2 -> if (isLeapYear)
                mDays = 29
            else
                mDays = 28
            else -> {
            }
        }
        return mDays
    }

    fun MonthCount(begin : String? =  "2015-01-01",end : String? = "2019-01-01") : Int{
        var timeformat = SimpleDateFormat("yyyy-MM-dd")
        val startTime = timeformat.parse(begin)
        val endTime = timeformat.parse(end)
        var dt = DateTime(startTime)
        val startyear = dt.year
        val startmonth = dt.monthOfYear
        dt = DateTime(endTime)
        val endyear = dt.year
        val endmonth = dt.monthOfYear
        val years = endyear - startyear
        return years * 12 - startmonth + 1 + endmonth
    }

}