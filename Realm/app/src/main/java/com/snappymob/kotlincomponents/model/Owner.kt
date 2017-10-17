package com.snappymob.kotlincomponents.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by ahmedrizwan on 10/17/17.
 *
 */
@RealmClass
open class Owner : RealmObject() {
    @Expose
    @PrimaryKey
    @SerializedName("login")
    open var login: String? = null

    @Expose
    @SerializedName("url")
    open var url: String? = null
}