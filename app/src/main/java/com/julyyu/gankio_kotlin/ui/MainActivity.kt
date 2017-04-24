package com.julyyu.gankio_kotlin.ui

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View


import butterknife.bindView
import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.http.ApiFactory
import com.julyyu.gankio_kotlin.http.GankApi
import com.julyyu.gankio_kotlin.http.GankResponse
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

    internal var fragmentManager: FragmentManager? = null
    internal var fragment: Fragment? = null
//    internal var mainFragment: MainFragment? = null
//    internal var ablumFragment: AblumFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        setSupportActionBar(toolbar)
        ApiFactory().getGankApi()
                .getGankIoData("Android",10,1)
                .enqueue(object : Callback<GankResponse> {
                    override fun onResponse(call: Call<GankResponse>, response: Response<GankResponse>?) {
                        if (response != null && response.isSuccessful()) {

                        }
                    }

                    override fun onFailure(call: Call<GankResponse>, t: Throwable) {

                    }
                })
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
        showFragment(tagMain)
//
//        navigationView!!.setNavigationItemSelectedListener(NavigationItemSelected())
    }

//    private inner class NavigationItemSelected : NavigationView.OnNavigationItemSelectedListener {
//
//        fun onNavigationItemSelected(item: MenuItem): Boolean {
//            when (item.itemId) {
//                R.id.navigation_main -> {
//                    item.isChecked = true
//                    showFragment(tagMain)
//                }
//                R.id.navigation_local_ablum -> {
//                    item.isChecked = true
//                    showFragment(tagAblum)
//                }
//                R.id.navigation_collection -> {
//                    item.isCheckable = true
//                    showFragment(tagCollection)
//                }
//                R.id.navigation_setting -> IntentUtil.goSettingActivity(this@MainActivity)
//                R.id.navigation_about -> IntentUtil.goAboutActivity(this@MainActivity)
//            }
//            drawerLayout!!.closeDrawer(GravityCompat.START)
//            return true
//        }
//    }
//
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
                    tagMain -> showFragment = GirlsFragment()
//                    tagAblum -> showFragment = AblumFragment()
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
                    tagMain -> showFragment = GirlsFragment()
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

    companion object {

        private val tagMain = "main"
        private val tagAblum = "ablum"
        private val tagCollection = "collection"
    }

}
