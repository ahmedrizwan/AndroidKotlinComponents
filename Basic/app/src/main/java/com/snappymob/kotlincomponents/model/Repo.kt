package com.snappymob.kotlincomponents.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index

import com.google.gson.annotations.SerializedName

@Entity(indices = arrayOf(Index("id"),
        Index("owner_login")),
        primaryKeys = arrayOf("name", "owner_login"))
data class Repo(
        var id: Int = 0,
        @SerializedName("name")
        var name: String? = "",
        @SerializedName("full_name")
        var fullName: String? = "",
        @SerializedName("description")
        var description: String? = "",
        @SerializedName("owner")
        @Embedded(prefix = "owner_")
        var owner: Owner? = null,
        @SerializedName("stargazers_count")
        var stars: Int = 0
) {

    data class Owner(
            @SerializedName("login")
            var login: String? = "",
            @SerializedName("url")
            var url: String? = ""
    )

    companion object {
        var UNKNOWN_ID = -1
    }
}