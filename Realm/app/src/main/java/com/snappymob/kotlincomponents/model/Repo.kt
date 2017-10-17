package com.snappymob.kotlincomponents.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by ahmedrizwan on 9/9/17.
 * Realm Model Class for Repo
 * TODO: Update/Change/Add realm model classes in this package
 */
@RealmClass
open class Repo : RealmObject() {
    @Expose
    @PrimaryKey
    open var id: Int = 0

    @Expose
    @SerializedName("name")
    open var name: String? = null

    @Expose
    @SerializedName("full_name")
    open var fullName: String? = null

    @Expose
    @SerializedName("description")
    open var description: String? = null

//    @Expose
//    @SerializedName("owner")
//    open var owner: Owner? = null

    @Expose
    @SerializedName("stargazers_count")
    open var stars: Int? = null
}
