package com.julyyu.gankio_kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.julyyu.gankio_kotlin.model.GankCollection

/**
 * Created by JulyYu on 2017/4/24.
 */
abstract class BaseAdapter<T,VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>{

    var datas: ArrayList<T>? = null


    constructor(datas: ArrayList<T>){
        this.datas = datas
    }

    override fun getItemCount(): Int = datas!!.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        bindViewHolder(holder,position, datas!![position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH {
        val view: View = LayoutInflater.from(parent!!.context).inflate(getViewHolderLayout(viewType),parent,false)
        return setViewHolder(view)
    }


    fun set(list: ArrayList<T>){
        this.datas = list
        notifyDataSetChanged()
    }

    fun add(list: ArrayList<T>){
        datas!!.addAll(list)
        notifyDataSetChanged()
    }

    abstract fun getViewHolderLayout(viewType: Int) : Int
    abstract fun setViewHolder(view: View) : VH
    abstract fun bindViewHolder(holder: VH, position: Int,data: T)
}