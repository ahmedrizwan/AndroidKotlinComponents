package com.snappymob.kotlincomponents.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Owner(
        @PrimaryKey
        @SerializedName("login")
        var login: String? = "",
        @SerializedName("url")
        var url: String? = ""
) : RealmModel