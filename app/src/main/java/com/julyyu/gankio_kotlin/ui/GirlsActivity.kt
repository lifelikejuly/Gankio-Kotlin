package com.julyyu.gankio_kotlin.ui

import android.app.WallpaperManager
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.ImageView
import butterknife.bindView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable

import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.model.Girl
import retrofit2.http.Url
import rx.Observable
import rx.Scheduler
import rx.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URI
import java.net.URL

class GirlsActivity : AppCompatActivity(){

    val viewPager : ViewPager by bindView(R.id.viewpager)

    var girlsAdapter : PagerAdapter ? = null
    var lookGirls : Array<ImageView?> ?= null
    var train : ArrayList<Girl> ?= null
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
        lookGirls = arrayOfNulls<ImageView>(train!!.size)
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

    private fun loveGirl() : Boolean{
        var boolContact : Boolean = true
        var filePath : String = ""
        val file = File(Environment.getExternalStorageDirectory(),"Girls")
        if(!file.exists()){
            try {
                file.mkdirs()
                filePath = file.absolutePath
            }catch (e : Exception){
                e.printStackTrace()
            }
        }else{
            filePath = file.absolutePath
        }
        var input : InputStream ?= null
        var output : OutputStream ?= null
        var girl : File ?= null
        var sweetGril = train!![viewPager!!.currentItem]
        var girlName = sweetGril.girlHome.toLowerCase().split("/".toRegex()).dropLastWhile ( {it.isEmpty()}).toTypedArray()
        try {
            var girlPhone = URL(sweetGril.girlHome)
            var con = girlPhone.openConnection()
            con.connectTimeout = 5 * 1000
            input = con.getInputStream()
            var bytes = ByteArray(1024)
            girl = File(filePath,girlName[girlName.size - 1])
            output = FileOutputStream(girl)
            var length : Int = 0
            do {
                length = input!!.read(bytes)
                if(length == -1){
                    continue
                }
                output.write(bytes,0,length)
            }while (length != -1)
        }catch (e : Exception){
            boolContact = false
            e.printStackTrace()
        }finally {
            try {
                if(input != null) input.close()
                if(output != null) output.close()
            }catch (e : Exception){
                e.printStackTrace()
            }
            val uri = Uri.fromFile(file)
            val scannerIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri)
            sendBroadcast(scannerIntent)
        }
        return boolContact
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_girl,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId){
            R.id.action_save -> {
                Observable.just("")
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .map {
                            it -> loveGirl()
                        }
                        .subscribe {
                            if(it){
                                Snackbar.make(viewPager,"妹子送到相册了",Snackbar.LENGTH_SHORT).show()
                            }else{
                                Snackbar.make(viewPager,"妹子没有送到相册",Snackbar.LENGTH_SHORT).show()
                            }
                        }

            }
            R.id.action_wallpaper -> {
                val wallpaper = (lookGirls!![viewPager.currentItem]!!.drawable as GlideBitmapDrawable).bitmap
                try {
                    WallpaperManager.getInstance(this).setBitmap(wallpaper)
                    Snackbar.make(viewPager,"壁纸设置成功",Snackbar.LENGTH_SHORT).show()
                }catch (e : Exception){
                    e.printStackTrace()
                    Snackbar.make(viewPager,"壁纸设置失败",Snackbar.LENGTH_SHORT).show()
                }
//                WallpaperManager.getInstance(this).getCropAndSetWallpaperIntent()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
