package com.julyyu.gankio_kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.model.Gank
import kotlinx.android.synthetic.main.item_meizi.view.*

/**
 * Created by JulyYu on 2017/4/24.
 */
class GirlAdapter(gank: ArrayList<Gank>) : BaseAdapter<Gank, GirlAdapter.GirlViewHolder>(gank) {


    override fun getViewHolderLayout(): Int {
        return R.layout.item_meizi
    }


    override fun setViewHolder(view: View): GirlViewHolder {
        return GirlViewHolder(view)
    }

    override fun bindViewHolder(holder: GirlViewHolder, position: Int, data: Gank) {
        Glide.with(holder.itemView.context)
                .load(data.url)
                .into(holder.itemView.iv_meizi)
    }


    inner class GirlViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}