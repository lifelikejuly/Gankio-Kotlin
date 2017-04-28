package com.julyyu.gankio_kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.Route
import com.julyyu.gankio_kotlin.model.Gank
import com.julyyu.gankio_kotlin.model.Girl
import kotlinx.android.synthetic.main.item_meizi.view.*

/**
 * Created by JulyYu on 2017/4/24.
 */
class GirlAdapter(girls: ArrayList<Girl>) : BaseAdapter<Girl, GirlAdapter.GirlViewHolder>(girls) {

    override fun getViewHolderLayout(viewType: Int): Int {
        return R.layout.item_meizi
    }

    override fun setViewHolder(view: View): GirlViewHolder {
        return GirlViewHolder(view)
    }

    override fun bindViewHolder(holder: GirlViewHolder, position: Int, data: Girl) {
        holder.itemView.iv_meizi.setOriginalSize(data.girlWidth,data.girlHeight)
        Glide.with(holder.itemView.context)
                .load(data.girlHome)
                .into(holder.itemView.iv_meizi)
        holder.itemView.setOnClickListener {
            Route().visitGirls(holder.itemView.context,datas!!,position)
        }
    }


    inner class GirlViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}