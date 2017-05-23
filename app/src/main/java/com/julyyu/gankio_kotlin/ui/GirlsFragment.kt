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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import butterknife.bindView
import com.julyyu.gankio_kotlin.R
import com.julyyu.gankio_kotlin.adapter.GirlAdapter
import com.julyyu.gankio_kotlin.http.ApiFactory
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
import rx.functions.Action1
import kotlin.collections.ArrayList

/**
 * Created by JulyYu on 2017/4/24.
 */
class GirlsFragment : Fragment(){

    internal var view: View? = null
    val swipeFreshLayout: SwipeRefreshLayout by bindView(R.id.swipelayout)
    val recyclerView: RecyclerView by bindView(R.id.recycler)
    var subscription: Subscription? = null
    var giradapter: GirlAdapter?= null
    var currentPage: Int = 1
    var isLoadingMore = false
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = LayoutInflater.from(activity).inflate(R.layout.view_recycler,null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscription = RxBus.observe<GirlGoEvent>()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { takeGirls(it.girls) }
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && !isLoadingMore) {
                    var lastVisiblePosition = 0
                    if (recyclerView!!.layoutManager is LinearLayoutManager) {
                        lastVisiblePosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    } else if (recyclerView.layoutManager is GridLayoutManager) {
                        lastVisiblePosition = (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                    } else if (recyclerView.layoutManager is StaggeredGridLayoutManager) {
                        val staggeredGridLayoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
                        val lastVisible = staggeredGridLayoutManager.findLastVisibleItemPositions(null)
                        lastVisiblePosition = lastVisible[lastVisible.size - 1]
                    }
                    if (lastVisiblePosition + 2 >= recyclerView.adapter!!.itemCount - 1) {
                        loadMore()
                    } else {
                        isLoadingMore = false
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
        swipeFreshLayout.isRefreshing = true
        loadMore()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
           subscription!!.unsubscribe()
    }
    fun loadMore(){
        isLoadingMore = true
        ApiFactory.getGankApi()
                .getGankIoData("福利",10,currentPage)
                .enqueue(object : Callback<GankResponse> {
                    override fun onResponse(call: Call<GankResponse>, response: Response<GankResponse>?) {
                        swipeFreshLayout.isRefreshing = false
                        isLoadingMore = false
                        if (response != null && response.isSuccessful()) {
                            currentPage++
                            cookGirls(response.body().results!!)
                        }
                    }
                    override fun onFailure(call: Call<GankResponse>, t: Throwable) {
                        swipeFreshLayout.isRefreshing = false
                        isLoadingMore = false
                        Snackbar.make(swipeFreshLayout,"妹子加载失败",Snackbar.LENGTH_SHORT).show()
                    }
                })
    }

    fun cookGirls(ganks : ArrayList<Gank>){
        val girlses = ArrayList<Girl>()
        for (gank in ganks) {
            girlses.add(Girl(gank.url!!))
        }
        val intent = Intent(activity, GirlsCookService::class.java)
        intent.putParcelableArrayListExtra("Girls", girlses as ArrayList<out Parcelable>)
        activity.startService(intent)
    }
    fun takeGirls(girls : ArrayList<Girl>){
        if(recyclerView.adapter == null){
            giradapter = GirlAdapter(girls)
            recyclerView.adapter = giradapter
        }else{
            giradapter!!.add(girls)
        }
    }
}