package com.julyyu.gankio_kotlin.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by JulyYu on 2017/4/25.
 */
data class Girl(var girlHome: String,var girlHeight: Int = 234,var girlWidth: Int = 345) : Parcelable {

    fun size(width: Int, height: Int) {
        this.girlWidth = width
        this.girlHeight = height
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Girl> = object : Parcelable.Creator<Girl> {
            override fun createFromParcel(source: Parcel): Girl = Girl(source)
            override fun newArray(size: Int): Array<Girl?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readInt(), source.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(girlHome)
        dest?.writeInt(girlHeight)
        dest?.writeInt(girlWidth)
    }
}