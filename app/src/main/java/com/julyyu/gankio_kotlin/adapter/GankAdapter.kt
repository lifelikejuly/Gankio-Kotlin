package com.julyyu.gankio_kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.model.Gank
import kotlinx.android.synthetic.main.item_dry.view.*
import kotlinx.android.synthetic.main.item_dry_pic.view.*
import kotlinx.android.synthetic.main.item_meizi.view.*
import java.util.*
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import com.julyyu.gankio_kotlin.App
import com.julyyu.gankio_kotlin.Route
import com.julyyu.gankio_kotlin.model.Girl
import com.julyyu.gankio_kotlin.ui.WebPageActivity
import kotlinx.android.synthetic.main.item_classfy.view.*
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList


/**
 * Created by JulyYu on 2017/4/26.
 *
 */
class GankAdapter(gank: ArrayList<Gank>) : BaseAdapter<Gank,GankAdapter.GankViewHolder>(gank){

    var timeFormat = SimpleDateFormat("yyyy/MM/dd")

    override fun bindViewHolder(holder: GankViewHolder, position: Int, data: Gank) {
        var type = getItemViewType(position)
        when(type){
            1 -> {
                Glide.with(App.context)
                        .load(data.url)
                        .into(holder.itemView.iv_meizi)
            }
            2 -> {
                holder.itemView.tv_title.text = data.desc
                holder.itemView.tv_via.text = data.who + " & " + data.type
            }
            3 -> {
                holder.itemView.tv_title2.text = data.desc
                holder.itemView.tv_via2.text = data.who + " & " + data.type
                Glide.with(App.context)
                        .load(data.images?.get(0))
                        .asBitmap()
                        .into(holder.itemView.iv_img)
            }
            4 -> {
                holder.itemView.tv_classfy.text = data.desc
            }
        }
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            if(type == 1){
                var girl: ArrayList<Girl> ?= ArrayList()
                girl!!.add(Girl(data.url!!,234,345))
                Route().visitGirls(holder.itemView.context,girl!!,0)
            }else if (type == 4){
                return@setOnClickListener
            }else{
                val intent = Intent(context, WebPageActivity::class.java)
                intent.putExtra("url", data.url)
                intent.putExtra("des", data.desc)
                context.startActivity(intent)
            }
        }
    }


    override fun getViewHolderLayout(viewType: Int): Int {
        when(viewType){
            1 -> return R.layout.item_meizi
            2 -> return R.layout.item_dry
            3 -> return R.layout.item_dry_pic
            4 -> return R.layout.item_classfy
            else -> return  R.layout.item_dry_pic
        }
    }

    override fun setViewHolder(view: View): GankViewHolder {
       return GankViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        var gank: Gank = datas!![position]
        var type = gank.type
        when(type){
            "福利"  -> return 1
            "classfy" -> return 4
            else   -> {
//                if (gank.images == null || gank.images!!.isEmpty()){
                    return 2
//                }else{
//                    return 3
//                }
            }
        }
        return super.getItemViewType(position)
    }

    inner class GankViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){

    }
}