package com.snappymob.kotlincomponents.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.support.annotation.NonNull
import com.google.gson.annotations.SerializedName

/**
 * Created by ahmedrizwan on 9/9/17.
 * Model Class for Repo
 * TODO: Update/Change/Add model classes in this package
 */
@Entity(indices = arrayOf(Index("id"),
        Index("owner_login")),
        primaryKeys = arrayOf("name","owner_login"))
class Repo(
        var id: Int = 0,
        @SerializedName("name")
        var name: String = "",
        @SerializedName("full_name")
        var fullName: String? = "",
        @SerializedName("description")
        var description: String? = "",
        @SerializedName("owner")
        @Embedded(prefix = "owner_")
        @NonNull
        var owner: Owner = Owner(),
        @SerializedName("stargazers_count")
        var stars: Int = 0
) {
        class Owner(
                @NonNull
                @SerializedName("login")
                var login: String = "",
                @SerializedName("url")
                var url: String? = ""
        )
}