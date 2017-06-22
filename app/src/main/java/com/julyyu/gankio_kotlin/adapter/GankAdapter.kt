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
import com.julyyu.gankio_kotlin.ui.WebPageActivity
import java.text.SimpleDateFormat


/**
 * Created by JulyYu on 2017/4/26.
 */
class GankAdapter(gank: ArrayList<Gank>) : BaseAdapter<Gank,GankAdapter.GankViewHolder>(gank){

    var timeFormat = SimpleDateFormat("yyyy/MM/dd")

    override fun bindViewHolder(holder: GankViewHolder, position: Int, data: Gank) {
        var type = getItemViewType(position)
        when(type){
            1 -> {
                Glide.with(holder.itemView.context)
                        .load(data.url)
                        .into(holder.itemView.iv_meizi)
            }
            2 -> {
                holder.itemView.tv_title.text = data.desc
                holder.itemView.tv_via.text = data.who + " & " + data.type
                holder.itemView.tv_time.text = timeFormat.format(data.publishedAt!!.time)
            }
            3 -> {
                holder.itemView.tv_title2.text = data.desc
                holder.itemView.tv_via2.text = data.who + " & " + data.type
                holder.itemView.tv_time2.text = timeFormat.format(data.publishedAt!!.time)
                Glide.with(holder.itemView.context)
                        .load(data.images?.get(0))
                        .into(holder.itemView.iv_img)
            }
        }
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, WebPageActivity::class.java)
            intent.putExtra("url", data.url)
            intent.putExtra("des", data.desc)
            context.startActivity(intent)
        }
    }


    override fun getViewHolderLayout(viewType: Int): Int {
        when(viewType){
            1 -> return R.layout.item_meizi
            2 -> return R.layout.item_dry
            3 -> return R.layout.item_dry_pic
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
            else   -> {
                if (gank.images == null || gank.images!!.isEmpty()){
                    return 2
                }else{
                    return 3
                }
            }
        }
        return super.getItemViewType(position)
    }

    inner class GankViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){

    }
}