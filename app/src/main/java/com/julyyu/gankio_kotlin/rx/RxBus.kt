package com.julyyu.gankio_kotlin.rx

import rx.Observable
import rx.Subscription
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subjects.Subject
import rx.subscriptions.CompositeSubscription
import java.util.*

/**
 * Created by JulyYu on 2017/4/25.
 */
object RxBus{

//    @Volatile private var instance: RxBus ?= null
    val bus = SerializedSubject(PublishSubject.create<Any>())
    private val subscriptionsMap: HashMap<Any, CompositeSubscription?> by lazy {
        HashMap<Any, CompositeSubscription?>()
    }

//    constructor(){
//        bus = SerializedSubject(PublishSubject.create())
//    }
//
//    fun getInstance() : RxBus{
//        if (instance == null) {
//            synchronized(RxBus::class.java) {
//                if (instance == null) {
//                    instance = RxBus()
//                }
//            }
//        }
//        return instance as RxBus
//    }

    fun post(o : Any){
        bus!!.onNext(o as Object?)
    }

    fun <T> toObservable(eventType: Class<T>): Observable<T> {
        return bus!!.ofType(eventType)
    }

    inline fun <reified T : Any> observe(): Observable<T> {
        return bus!!.ofType(T::class.java)
    }

    fun unregister(subscriber: Any) {
        val compositeSubscription = subscriptionsMap[subscriber]
        if (compositeSubscription == null) {
//            Log.w(TAG, "Trying to unregister subscriber that wasn't registered")
        } else {
            compositeSubscription.clear()
            subscriptionsMap.remove(subscriber)
        }
    }

    internal fun register(subscriber: Any, subscription: Subscription) {
        var compositeSubscription = subscriptionsMap[subscriber]
        if (compositeSubscription == null) {
            compositeSubscription = CompositeSubscription()
        }
        compositeSubscription.add(subscription)
        subscriptionsMap[subscriber] = compositeSubscription
    }

    fun Subscription.registerInBus(subscriber: Any) {
        RxBus.register(subscriber, this)
    }


}

