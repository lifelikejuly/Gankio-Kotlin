package com.julyyu.gankio_kotlin.ui


import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageButton
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.Route
import com.julyyu.gankio_kotlin.http.ApiFactory
import com.julyyu.gankio_kotlin.http.GankResponse
import com.julyyu.gankio_kotlin.model.Girl
import com.julyyu.gankio_kotlin.util.UpdateUtil
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by JulyYu on 2017/2/3.
 * 主页
 */

class MainActivity : ThemeActivity(),Toolbar.OnMenuItemClickListener,View.OnClickListener{


//    val toolbar: Toolbar by bindView(R.id.toolbar)
//    val navigationView: NavigationView by bindView(R.id.navigation_view)
//    val drawerLayout: DrawerLayout by bindView(R.id.drawerlayout)

    var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    var headImage: ImageView? = null
    var headFresh: ImageButton? = null

    var fragmentManager: FragmentManager? = null
    var fragment: Fragment? = null
    var menu : Menu? = null

    var singleLady : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar!!.setNavigationIcon(R.drawable.ic_dehaze)
        toolbar.setOnMenuItemClickListener(this@MainActivity)
        toolbar.title = "text"
        actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.open, R.string.close) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }
        }
        (actionBarDrawerToggle as ActionBarDrawerToggle).syncState()
        drawerlayout!!.addDrawerListener(actionBarDrawerToggle!!)
        fragmentManager = supportFragmentManager
        showFragment(tagDaily)
        navigation_view!!.setNavigationItemSelectedListener(NavigationItemSelected())
        val headView = navigation_view.inflateHeaderView(R.layout.drawer_header)
        headImage = headView.findViewById<ImageView>(R.id.iv_img)
        headFresh = headView.findViewById<ImageButton>(R.id.iv_fresh)
        headFresh!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                takeSingleLady()
            }
        })
        headImage!!.setOnClickListener(this@MainActivity)
        takeSingleLady()
        UpdateUtil().checkUpdate(this@MainActivity)
    }

    private inner class NavigationItemSelected : NavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.navigation_daily -> {
                    item.isChecked = true
                    showFragment(tagDaily)
                    checkOptionMenu(true)
                    drawerlayout!!.closeDrawer(GravityCompat.START)
                }
                R.id.navigation_girls -> {
                    item.isChecked = true
                    showFragment(tagGirl)
                    checkOptionMenu(false)
                    drawerlayout!!.closeDrawer(GravityCompat.START)
                }
                R.id.navigation_about -> {
                    Route().about(this@MainActivity)
                }
                R.id.navigation_setting -> {
                    Route().setting(this@MainActivity)
                }
//                R.id.navigation_about -> IntentUtil.goAboutActivity(this@MainActivity)
            }

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
//                        supportActionBar!!.setTitle("Daily")
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
//                        supportActionBar!!.setTitle("Daily")
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
                            singleLady = response.body()!!.results!![0].url
                            Glide.with(this@MainActivity)
                                    .load(singleLady)
                                    .into(headImage)
                        }
                    }

                    override fun onFailure(call: Call<GankResponse>?, t: Throwable?) {
                        freshRotateAnimation(false)
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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater : MenuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.action_calendar -> run {
                Route().Calendar(this@MainActivity)
            }
        }
        return true
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.iv_img ->{
                if(!TextUtils.isEmpty(singleLady)){
                    var girl: ArrayList<Girl> ?= ArrayList()
                    girl!!.add(Girl(singleLady!!,234,345))
                    Route().visitGirls(this@MainActivity,girl!!,0)
                }
            }
        }
    }


    private fun checkOptionMenu(boolean: Boolean) {
        if (null != menu) {
            if (boolean) {
                for (i in 0..menu!!.size() - 1) {
                    menu!!.getItem(i).setVisible(true)
                    menu!!.getItem(i).setEnabled(true)
                }
            } else {
                for (i in 0..menu!!.size() - 1) {
                    menu!!.getItem(i).setVisible(false)
                    menu!!.getItem(i).setEnabled(false)
                }
            }
        }
    }
}
