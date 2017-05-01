package com.julyyu.gankio_kotlin.ui

import android.media.Image
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageButton
import android.widget.ImageView


import butterknife.bindView
import com.bumptech.glide.Glide
import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.Route
import com.julyyu.gankio_kotlin.http.ApiFactory
import com.julyyu.gankio_kotlin.http.GankApi
import com.julyyu.gankio_kotlin.http.GankResponse
import com.julyyu.gankio_kotlin.util.UpdateUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by JulyYu on 2017/2/3.
 */

class MainActivity : AppCompatActivity() {

    internal val toolbar: Toolbar by bindView(R.id.toolbar)
    internal val navigationView: NavigationView by bindView(R.id.design_navigation_view)
    internal val drawerLayout: DrawerLayout by bindView(R.id.drawerlayout)
    internal var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    internal var headImage: ImageView? = null
    internal var headFresh: ImageButton? = null

    internal var fragmentManager: FragmentManager? = null
    internal var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar!!.setNavigationIcon(R.drawable.ic_dehaze)
        actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
            }

            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)
            }
        }
        (actionBarDrawerToggle as ActionBarDrawerToggle).syncState()
        drawerLayout!!.addDrawerListener(actionBarDrawerToggle!!)
        fragmentManager = supportFragmentManager
        showFragment(tagDaily)
        navigationView!!.setNavigationItemSelectedListener(NavigationItemSelected())
        val headView = navigationView.inflateHeaderView(R.layout.drawer_header)
        headImage = headView.findViewById(R.id.iv_img) as ImageView
        headFresh = headView.findViewById(R.id.iv_fresh) as ImageButton
        headFresh!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
//                freshRotateAnimation(true)
                takeSingleLady()
            }
        })
        takeSingleLady()
    }

    private inner class NavigationItemSelected : NavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.navigation_daily -> {
                    item.isChecked = true
                    showFragment(tagDaily)
                }
                R.id.navigation_girls -> {
                    item.isChecked = true
                    showFragment(tagGirl)
                }
                R.id.navigation_about -> {
                    Route().about(this@MainActivity)
                }
//                R.id.navigation_setting -> IntentUtil.goSettingActivity(this@MainActivity)
//                R.id.navigation_about -> IntentUtil.goAboutActivity(this@MainActivity)
            }
            drawerLayout!!.closeDrawer(GravityCompat.START)
            return true
        }
    }

    private fun showFragment(tag: String) {
        if (fragment != null) {
            var showFragment: Fragment? = fragmentManager!!.findFragmentByTag(tag)
            if (showFragment != null) {
                fragmentManager!!.beginTransaction()
                        .hide(fragment)
                        .show(showFragment)
                        .commit()
            } else {
                when (tag) {
                    tagGirl -> {
                        showFragment = GirlsFragment()
                        supportActionBar!!.setTitle("Girl")
                    }
                    tagDaily -> {
                        showFragment = GankFragment()
                        supportActionBar!!.setTitle("Daily")
                    }
//                    tagCollection -> showFragment = CollectionsFragment()
                }
                fragmentManager!!.beginTransaction()
                        .hide(fragment)
                        .add(R.id.framelayout, showFragment)
                        .show(showFragment)
                        .commit()
            }
            fragment = showFragment
        } else {
            var showFragment: Fragment? = fragmentManager!!.findFragmentByTag(tag)
            if (showFragment != null) {
                fragmentManager!!.beginTransaction()
                        .show(showFragment)
                        .commit()
            } else {
                when (tag) {
                    tagGirl -> {
                        showFragment = GirlsFragment()
                        supportActionBar!!.setTitle("Girl")
                    }
                    tagDaily -> {
                        showFragment = GankFragment()
                        supportActionBar!!.setTitle("Daily")
                    }
//                    tagAblum -> showFragment = AblumFragment()
//                    tagCollection -> showFragment = CollectionsFragment()
                }
                fragmentManager!!.beginTransaction()
                        .add(R.id.framelayout, showFragment)
                        .show(showFragment)
                        .commit()
            }
            fragment = showFragment
        }
    }
    private fun takeSingleLady(){
        freshRotateAnimation(true)
        ApiFactory.getGankApi()
                .getRandomGirl()
                .enqueue(object : Callback<GankResponse>{
                    override fun onResponse(call: Call<GankResponse>?, response: Response<GankResponse>?) {
                        freshRotateAnimation(false)
                        if(response!!.isSuccessful){
                            Glide.with(this@MainActivity)
                                    .load(response.body()!!.results!![0].url)
                                    .into(headImage)

                        }
                    }

                    override fun onFailure(call: Call<GankResponse>?, t: Throwable?) {
                        freshRotateAnimation(false)
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })
    }

    private fun freshRotateAnimation(boolean: Boolean){
        if(boolean){
            val rotateAnimation = RotateAnimation(0f, 360f, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f)
            rotateAnimation.duration = 500
            rotateAnimation.repeatMode = Animation.RESTART
            rotateAnimation.repeatCount = Animation.INFINITE
            headFresh!!.startAnimation(rotateAnimation)
        }else{
            headFresh!!.clearAnimation()
        }
    }

    companion object {

        private val tagMain = "main"
        private val tagAblum = "ablum"
        private val tagCollection = "collection"

        private val tagGirl = "girl"
        private val tagDaily = "daily"
    }

    override fun onResume() {
        super.onResume()
        UpdateUtil().checkUpdate(this@MainActivity)
    }

}
