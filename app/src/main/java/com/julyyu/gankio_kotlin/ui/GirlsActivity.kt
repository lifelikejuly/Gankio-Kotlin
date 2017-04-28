package com.julyyu.gankio_kotlin.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
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
    var lookGirls = emptyArray<ImageView>()
    var train : ArrayList<Girl> ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_girls)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        train = intent.getParcelableArrayListExtra<Girl>("girls")
        girlsAdapter = object : PagerAdapter(){

            override fun instantiateItem(container: ViewGroup?, position: Int): Any {
                val lookGirl = ImageView(this@GirlsActivity)
                lookGirl.adjustViewBounds = true
                Glide.with(this@GirlsActivity)
                        .load(train!![position].girlHome)
                        .fitCenter()
                        .into(lookGirl)
                container!!.addView(lookGirl)
                lookGirls[position] = lookGirl
                return super.instantiateItem(container, position)
            }

            override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
                return view == `object`
            }

            override fun getCount(): Int {
                return lookGirls!!.size
            }



        }
        viewPager.adapter = girlsAdapter

    }

}
