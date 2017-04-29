package com.julyyu.gankio_kotlin.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.bindView
import com.bumptech.glide.Glide

import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.model.Girl

class GirlsActivity : AppCompatActivity() {

    internal val viewPager : ViewPager by bindView(R.id.viewpager)

    var girlsAdapter : PagerAdapter ? = null
//    var lookGirls : Array<ImageView> ?= null
    var train : ArrayList<Girl> ? = null
    var flag : Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_girls)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setShowHideAnimationEnabled(true)
        hideOrShowToolBar()
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            this.finish()
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
        }

        train = intent.getParcelableArrayListExtra<Girl>("girls")
        var position = intent.getIntExtra("position",0)
        var lookGirls = arrayOfNulls<ImageView>(train!!.size)
        girlsAdapter = object : PagerAdapter(){

            override fun instantiateItem(container: ViewGroup?, position: Int): Any {
                val lookGirl = ImageView(this@GirlsActivity)
                val layout = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                lookGirl.adjustViewBounds = true
                lookGirl.layoutParams = layout
                Glide.with(this@GirlsActivity)
                        .load(train!![position].girlHome)
                        .fitCenter()
                        .into(lookGirl)
                container!!.addView(lookGirl)
                lookGirls!![position] = lookGirl
//                lookGirls[position] = lookGirl
                return lookGirl
            }

            override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
                return view === `object`
            }

            override fun getCount(): Int {
                return train!!.size
            }

            override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
                container!!.removeView(lookGirls!![position])
            }

        }
        viewPager.adapter = girlsAdapter
        viewPager.currentItem = position
        viewPager.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when(event!!.action){
                    MotionEvent.ACTION_DOWN -> {
                        flag = true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        flag = false
                        supportActionBar!!.hide()
                        hideSystemUi()
                    }
                    MotionEvent.ACTION_UP -> {
                        if(flag){
                            hideOrShowToolBar()
                        }
                    }
                }
                return false
            }
        })
    }

    private fun hideOrShowToolBar(){
        if(supportActionBar!!.isShowing){
            supportActionBar!!.hide()
            hideSystemUi()
        }else{
            supportActionBar!!.show()
            showSystemUi()
        }
    }

    private fun showSystemUi(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    private fun hideSystemUi(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }

}
