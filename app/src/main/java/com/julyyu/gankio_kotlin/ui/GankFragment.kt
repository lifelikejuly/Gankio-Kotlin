package com.julyyu.gankio_kotlin.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotterknife.bindView
import com.julyyu.gankio_kotlin.AppConst
import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.adapter.GankAdapter
import com.julyyu.gankio_kotlin.http.ApiFactory
import com.julyyu.gankio_kotlin.model.Gank
import com.julyyu.gankio_kotlin.rx.RxBus
import com.julyyu.gankio_kotlin.rx.event.GankEvent
import com.julyyu.gankio_kotlin.util.SpUtil
import kotlinx.android.synthetic.main.activity_main.*
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by JulyYu on 2017/4/26.
 */
class GankFragment: Fragment(){

    internal var view: View? = null
    internal val swipeFreshLayout: SwipeRefreshLayout by bindView(R.id.swipelayout)
    internal val recyclerView: RecyclerView by bindView(R.id.recycler)
    var subscription: Subscription? = null

    var androids : Boolean by SpUtil("android",true)
    var ios     : Boolean by SpUtil("ios",true)
    var front   : Boolean by SpUtil("front",false)
    var meizi   : Boolean by SpUtil("meizi",true)
    var video   : Boolean by SpUtil("video",false)
    var other   : Boolean by SpUtil("other",false)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = LayoutInflater.from(activity).inflate(R.layout.view_recycler,null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        swipeFreshLayout.isRefreshing = true
        loadMore()
        swipeFreshLayout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                loadMore()
            }
        })
        subscription = RxBus.observe<GankEvent>()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    activity.toolbar.title = it.day
                    ApiFactory.getGankApi()
                        .getGankDayDate(it.day?.replace("-","/")!!)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            swipeFreshLayout.isRefreshing = false
                            if(!it.error){
                                val ganks = ArrayList<Gank>()
                                if(it.results!!.Android != null && androids){
                                    ganks.add(Gank("Android","classfy"))
                                    ganks.addAll(it.results!!.Android!!)
                                }
                                if(it.results!!.iOS != null && ios){
                                    ganks.add(Gank("iOS","classfy"))
                                    ganks.addAll(it.results!!.iOS!!)
                                }
                                if(it.results!!.前端 != null && front){
                                    ganks.add(Gank("前端","classfy"))
                                    ganks.addAll(it.results!!.前端!!)
                                }
                                if(it.results!!.休息视频 != null && video){
                                    ganks.add(Gank("休息视频","classfy"))
                                    ganks.addAll(it.results!!.休息视频!!)
                                }
                                if(it.results!!.拓展资源 != null && other){
                                    ganks.add(Gank("拓展资源","classfy"))
                                    ganks.addAll(it.results!!.拓展资源!!)
                                }
                                if(it.results!!.福利 != null && meizi){
                                    ganks.add(Gank("Girl","classfy"))
                                    ganks.addAll(it.results!!.福利!!)
                                }
                                recyclerView.adapter = GankAdapter(ganks)
                            }
                        } }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    fun loadMore(){
        ApiFactory.getGankApi()
                .getGankHistory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            if(!it.error) {
                                AppConst.dates = LinkedList(it.results)
                                val date: String? = it.results?.get(0)
                                activity.toolbar.title = date
                                ApiFactory.getGankApi()
                                        .getGankDayDate(date?.replace("-","/")!!)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe {
                                            swipeFreshLayout.isRefreshing = false
                                            if(!it.error){
                                                val ganks = ArrayList<Gank>()
                                                if(it.results!!.Android != null && androids){
                                                    ganks.add(Gank("Android","classfy"))
                                                    ganks.addAll(it.results!!.Android!!)
                                                }
                                                if(it.results!!.iOS != null && ios){
                                                    ganks.add(Gank("iOS","classfy"))
                                                    ganks.addAll(it.results!!.iOS!!)
                                                }
                                                if(it.results!!.前端 != null && front){
                                                    ganks.add(Gank("前端","classfy"))
                                                    ganks.addAll(it.results!!.前端!!)
                                                }
                                                if(it.results!!.休息视频 != null && video){
                                                    ganks.add(Gank("休息视频","classfy"))
                                                    ganks.addAll(it.results!!.休息视频!!)
                                                }
                                                if(it.results!!.拓展资源 != null && other){
                                                    ganks.add(Gank("拓展资源","classfy"))
                                                    ganks.addAll(it.results!!.拓展资源!!)
                                                }
                                                if(it.results!!.福利 != null && meizi){
                                                    ganks.add(Gank("Girl","classfy"))
                                                    ganks.addAll(it.results!!.福利!!)
                                                }
                                                recyclerView.adapter = GankAdapter(ganks)
                                            }
                                        }
                            }
                        },
                        {
                            error ->
                            swipeFreshLayout.isRefreshing = false
                            Snackbar.make(swipeFreshLayout,"日常加载失败",Snackbar.LENGTH_SHORT).show()
                            Log.i("ERROR",error.message)
                        }
                )
    }

}