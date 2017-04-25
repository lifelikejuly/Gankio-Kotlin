package com.julyyu.gankio_kotlin.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.julyyu.gankio_kotlin.model.Girl
import com.julyyu.gankio_kotlin.rx.RxBus
import com.julyyu.gankio_kotlin.rx.event.GirlGoEvent
import rx.Observable
import rx.functions.Action1
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.ExecutionException
import kotlin.collections.ArrayList

/**
 * Created by JulyYu on 2017/4/25.
 */
class GirlsCookService : Service() {

    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val girls = intent!!.getParcelableArrayListExtra<Girl>("Girls")
        Observable.just<ArrayList<Girl>>(girls)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe { girls ->
                    for (girl in girls) {
                        try {
                            val bitmap = Glide.with(applicationContext)
                                    .load(girl.girlHome)
                                    .asBitmap()
                                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                    .get()
                            girl.size(bitmap.getWidth(), bitmap.getHeight())
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        } catch (e: ExecutionException) {
                            e.printStackTrace()
                        }

                    }
                    RxBus.post(GirlGoEvent(girls,""))
                    stopSelf()
                }
        return super.onStartCommand(intent, flags, startId)
    }

}