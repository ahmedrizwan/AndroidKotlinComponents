package com.snappymob.kotlincomponents.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Repo(
        @PrimaryKey
        var id: Int = 0,

        @SerializedName("name")
        var name: String? = "",

        @SerializedName("full_name")
        var fullName: String? = "",

        @SerializedName("description")
        var description: String? = "",

        @SerializedName("owner")
        var owner: Owner? = null,

        @SerializedName("stargazers_count")
        var stars: Int = 0
) : RealmModel