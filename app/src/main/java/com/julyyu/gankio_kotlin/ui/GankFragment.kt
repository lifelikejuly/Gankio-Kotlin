package com.julyyu.gankio_kotlin.ui

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import butterknife.bindView
import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.adapter.GankAdapter
import com.julyyu.gankio_kotlin.adapter.GirlAdapter
import com.julyyu.gankio_kotlin.http.ApiFactory
import com.julyyu.gankio_kotlin.http.GankDateResponse
import com.julyyu.gankio_kotlin.http.GankHistoryResponse
import com.julyyu.gankio_kotlin.http.GankResponse
import com.julyyu.gankio_kotlin.model.Gank
import com.julyyu.gankio_kotlin.model.Girl
import com.julyyu.gankio_kotlin.rx.RxBus
import com.julyyu.gankio_kotlin.rx.event.GirlGoEvent
import com.julyyu.gankio_kotlin.service.GirlsCookService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rx.Scheduler
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat

/**
 * Created by JulyYu on 2017/4/26.
 */
class GankFragment: Fragment(){

    internal var view: View? = null
    internal val swipeFreshLayout: SwipeRefreshLayout by bindView(R.id.swipelayout)
    internal val recyclerView: RecyclerView by bindView(R.id.recycler)
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = LayoutInflater.from(activity).inflate(R.layout.view_recycler,null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        loadMore()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    fun loadMore(){
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
        val daily = simpleDateFormat.format(System.currentTimeMillis())
//                                        ApiFactory.getGankApi()
//                                        .getGankDayDate("2017/04/25")
//                                        .subscribeOn(Schedulers.io())
//                                        .observeOn(AndroidSchedulers.mainThread())
//                                        .subscribe {
//                                            if(!it.error){
//                                                val ganks = ArrayList<Gank>()
//                                                if(it.results!!.Android != null){
//                                                    ganks.addAll(it.results!!.Android!!)
//                                                }
//                                                if(it.results!!.iOS != null){
//                                                    ganks.addAll(it.results!!.iOS!!)
//                                                }
//                                                if(it.results!!.福利 != null){
//                                                    ganks.addAll(it.results!!.福利!!)
//                                                }
//                                                recyclerView.adapter = GankAdapter(ganks)
//                                            }
//                                        }

        ApiFactory.getGankApi()
                .getGankHistory()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            if(!it.error) {
                                val date: String? = it.results?.get(0)
                                ApiFactory.getGankApi()
                                        .getGankDayDate(date?.replace("-","/")!!)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe {
                                            if(!it.error){
                                                val ganks = ArrayList<Gank>()
                                                if(it.results!!.Android != null){
                                                    ganks.addAll(it.results!!.Android!!)
                                                }
                                                if(it.results!!.iOS != null){
                                                    ganks.addAll(it.results!!.iOS!!)
                                                }
                                                if(it.results!!.拓展资源 != null){
                                                    ganks.addAll(it.results!!.拓展资源!!)
                                                }
                                                if(it.results!!.福利 != null){
                                                    ganks.addAll(it.results!!.福利!!)
                                                }
                                                recyclerView.adapter = GankAdapter(ganks)
                                            }
                                        }
                            }
                        },
                        {
                            error ->
                            Log.i("ERROR",error.message)
                        }
                )
//        ApiFactory().getGankApi()
//                .getGankDayDate("2017/04/25")
//                .enqueue(object : Callback<GankDateResponse> {
//                    override fun onResponse(call: Call<GankDateResponse>, response: Response<GankDateResponse>?) {
//                        if (response != null && response.isSuccessful()) {
//                            val ganks = ArrayList<Gank>()
//                            if(response.body().results!!.Android != null){
//                                ganks.addAll(response.body().results!!.Android!!)
//                            }
//                            if(response.body().results!!.iOS != null){
//                                ganks.addAll(response.body().results!!.iOS!!)
//                            }
//                            if(response.body().results!!.福利 != null){
//                                ganks.addAll(response.body().results!!.福利!!)
//                            }
////                            if(response.body().results!!.Android != null){
////                                ganks.addAll(response.body().results!!.Android!!)
////                            }
//                            recyclerView.adapter = GankAdapter(ganks)
//                        }
//                    }
//                    override fun onFailure(call: Call<GankDateResponse>, t: Throwable) {
//                        Snackbar.make(swipeFreshLayout,"日常加载失败", Snackbar.LENGTH_SHORT).show()
//                    }
//                })
    }

}