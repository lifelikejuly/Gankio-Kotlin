package com.julyyu.gankio_kotlin

import android.content.Context
import android.content.Intent
import com.julyyu.gankio_kotlin.model.Girl
import com.julyyu.gankio_kotlin.ui.AboutActivity
import com.julyyu.gankio_kotlin.ui.GirlsActivity

/**
 * Created by julyyu on 2017/4/28.
 */
class Route{

    fun visitGirls(context : Context,girls : ArrayList<Girl>,position : Int){
        val intent = Intent(context,GirlsActivity::class.java)
        intent.putExtra("position",position)
        intent.putParcelableArrayListExtra("girls",girls)
        context.startActivity(intent)
    }

    fun about(context : Context){
        val intent = Intent(context,AboutActivity::class.java)
        context.startActivity(intent)
    }
}