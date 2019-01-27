package com.julyyu.gankio_kotlin.rx

import rx.Observable
import rx.Subscription
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subscriptions.CompositeSubscription
import java.util.*

/**
 * Created by JulyYu on 2017/4/25.
 */
object RxBus{

    val bus = SerializedSubject(PublishSubject.create<Any>())
    private val subscriptionsMap: HashMap<Any, CompositeSubscription?> by lazy {
        HashMap<Any, CompositeSubscription?>()
    }

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

