package com.julyyu.gankio_kotlin.ui.calendar

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.adapter.MonthPagerAdapter
import com.julyyu.gankio_kotlin.widget.VerticalViewPager
import org.joda.time.DateTime

/**
 * Created by JulyYu on 2017/5/24.
 */
class MonthsFragment : Fragment(), ViewPager.OnPageChangeListener {

    internal var view: android.view.View = null!!
    var verticalViewPager: VerticalViewPager
    var monthPagerAdapter: MonthPagerAdapter

    private var mNowYear: Int = 0
    private var mNowMonth: Int = 0
    private var mNowDay: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater!!.inflate(R.layout.fragment_months, container, false)
        verticalViewPager = view.findViewById(R.id.vertical_viewpager) as VerticalViewPager
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        monthPagerAdapter = MonthPagerAdapter(activity.supportFragmentManager)
        verticalViewPager.setAdapter(monthPagerAdapter)
        val dt = DateTime()
        verticalViewPager.setCurrentItem(monthPagerAdapter.getCount() / 2 + dt.getMonthOfYear() - 1)
        verticalViewPager.setOnPageChangeListener(this@MonthsFragment)
        val dateTime = DateTime()
        mNowYear = dateTime.getYear()
        mNowMonth = dateTime.getMonthOfYear()
        mNowDay = dateTime.getDayOfMonth()
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
//        if (yearmonthListener != null) {
//            yearmonthListener!!.getYearMonth(monthPagerAdapter.getYearMonth(position))
//        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

//    fun setYearmonthListener(yearmonthListener: MonthsFragment.YearmonthListener) {
//        this.yearmonthListener = yearmonthListener
//    }
//
//    interface YearmonthListener {
//        fun getYearMonth(yearmonth: String)
//    }

}