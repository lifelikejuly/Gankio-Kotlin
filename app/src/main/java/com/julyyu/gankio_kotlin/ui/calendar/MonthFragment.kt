package com.julyyu.gankio_kotlin.ui.calendar

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.adapter.MonthAdapter
import com.julyyu.gankio_kotlin.model.Day
import com.julyyu.gankio_kotlin.rx.RxBus
import com.julyyu.gankio_kotlin.rx.event.GankEvent
import com.julyyu.gankio_kotlin.util.CalendarUtil
import kotlinx.android.synthetic.main.fragment_month.*
import org.joda.time.DateTime

/**
 * Created by JulyYu on 2017/5/24.
 */
open class MonthFragment : Fragment(),MonthAdapter.MonthListener{


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    companion object{
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        val ARG_SECTION_YEAR = "year_number"
        val ARG_SECTION_MONTH = "month_number"


        fun newInstance(year: Int, month: Int): MonthFragment {
            val fragment = MonthFragment()
            val args = Bundle()
            args.putInt(MonthFragment.Companion.ARG_SECTION_YEAR, year)
            args.putInt(MonthFragment.Companion.ARG_SECTION_MONTH, month)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_month, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val year = arguments.getInt(MonthFragment.Companion.ARG_SECTION_YEAR)
        val month = arguments.getInt(MonthFragment.Companion.ARG_SECTION_MONTH)
        recycler.layoutManager = GridLayoutManager(activity, 7) as RecyclerView.LayoutManager?
        val days = ArrayList<Day>()
        var dateTime = DateTime()
        dateTime = dateTime.withDate(year, month, 1)
        val daysOfMonth = CalendarUtil().MonthDays(dateTime.monthOfYear().isLeap(), month)
        val dayofweek = if (dateTime.getDayOfWeek() === 7) 0 else dateTime.getDayOfWeek()
        val tabledays = daysOfMonth + dayofweek
        var day = 1
        for (i in 0..tabledays - 1) {
            if (i >= dayofweek) {
                days.add(Day(i,year,month,day))
                day++
            } else {
                days.add(Day(i,0,0,0))
            }
        }
        recycler.adapter = MonthAdapter(days)
        (recycler.adapter as MonthAdapter).listener = this
    }
    override fun monthClickListener(day: String) {
        RxBus.post(GankEvent(day))
        activity!!.finish()
    }
}