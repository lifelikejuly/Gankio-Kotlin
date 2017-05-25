package com.julyyu.gankio_kotlin.model

/**
 * Created by JulyYu on 2017/5/24.
 */
class Day{
    var count: Int
    var year: Int
    var month: Int
    var day: Int
    constructor(count: Int,year: Int,month: Int, day: Int) {
        this.count = count
        this.year = year
        this.month = month
        this.day = day
    }

    fun getday() : String{
        return year.toString() + "-" +
                (if(month.toString().length == 1)  "0" + month.toString() else month.toString()) + "-" +
                (if(day.toString().length == 1) "0" + day.toString() else day.toString())
    }

}