package com.julyyu.gankio_kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.julyyu.gankio_kotlin.AppConst
import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.model.Day
import kotlinx.android.synthetic.main.item_calendar_day.view.*

/**
 * Created by JulyYu on 2017/5/24.
 */
class MonthAdapter(days : ArrayList<Day>) : BaseAdapter<Day, MonthAdapter.MonthViewHolder>(days){

    var listener : MonthListener ?= null


    override fun getViewHolderLayout(viewType: Int): Int {
        return R.layout.item_calendar_day
    }

    override fun setViewHolder(view: View): MonthViewHolder {
        return MonthViewHolder(view)
    }

    override fun bindViewHolder(holder: MonthViewHolder, position: Int, data: Day) {
        if(data.day != 0){
            holder.itemView.tv_day.setText(data.day.toString())
        }else{
            holder.itemView.tv_day.setText("")
        }
        if(AppConst.dates!!.contains(data.getday())){
            holder.itemView.iv_have.visibility = View.VISIBLE
        }else{
            holder.itemView.iv_have.visibility = View.INVISIBLE
        }
        holder.itemView.setOnClickListener {
            v: View? ->
            if(AppConst.dates!!.contains(data.getday()))listener!!.monthClickListener(data.getday())
        }

    }

    inner class MonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    interface MonthListener{
        fun monthClickListener(day : String)
    }
}