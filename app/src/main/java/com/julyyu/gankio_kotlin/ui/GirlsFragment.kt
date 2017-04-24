package com.julyyu.gankio_kotlin.ui

import android.os.Bundle
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by JulyYu on 2017/4/24.
 */
class GirlsFragment : Fragment(){

    internal var view: View? = null
    internal val swipeFreshLayout: SwipeRefreshLayout by bindView(R.id.swipelayout)
    internal val recyclerView: RecyclerView by bindView(R.id.recycler)
    var giradapter: GirlAdapter?= null
    var currentPage: Int = 1
    var isLoadingMore = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = LayoutInflater.from(activity).inflate(R.layout.view_recycler,null)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && !isLoadingMore) {
                    isLoadingMore = true
                    var lastVisiblePosition = 0
                    if (recyclerView!!.layoutManager is LinearLayoutManager) {
                        lastVisiblePosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    } else if (recyclerView.layoutManager is GridLayoutManager) {
                        lastVisiblePosition = (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                    } else if (recyclerView.layoutManager is StaggeredGridLayoutManager) {
                        val staggeredGridLayoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
                        val lastVisible = staggeredGridLayoutManager.findLastVisibleItemPositions(IntArray(staggeredGridLayoutManager.spanCount))
                        lastVisiblePosition = lastVisible[lastVisible.size - 1]
                    }
                    if (lastVisiblePosition == recyclerView.adapter!!.itemCount - 1) {
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
        loadMore();
    }
    fun loadMore(){
        ApiFactory().getGankApi()
                .getGankIoData("福利",10,currentPage)
                .enqueue(object : Callback<GankResponse> {
                    override fun onResponse(call: Call<GankResponse>, response: Response<GankResponse>?) {
                        if (response != null && response.isSuccessful()) {
                            currentPage++
                            if (recyclerView.adapter == null){
                                giradapter = GirlAdapter(response.body().results!!)
                                recyclerView?.adapter = giradapter
                            }else{
                                giradapter!!.add(response.body().results!!)
                            }
                        }
                    }

                    override fun onFailure(call: Call<GankResponse>, t: Throwable) {

                    }
                })
    }
}