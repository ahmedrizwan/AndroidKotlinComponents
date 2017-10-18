package com.snappymob.kotlincomponents.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by ahmedrizwan on 10/18/17.
 *
 */

open class Repo : RealmObject() {

    @PrimaryKey
    var id: Int = 0

    var name: String? = null

    var owner: Owner? = null

    @SerializedName("stargazers_count")
    var stars: Int = 0
}

open class Owner : RealmObject() {
    @Expose
    @PrimaryKey
    @SerializedName("login")
    open var login: String? = null

    @Expose
    @SerializedName("url")
    open var url: String? = null
}
