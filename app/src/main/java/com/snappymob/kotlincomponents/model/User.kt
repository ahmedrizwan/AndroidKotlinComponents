package com.snappymob.kotlincomponents.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import com.google.gson.annotations.SerializedName

@Entity
class User(
        @PrimaryKey
        @SerializedName("login")
        var login: String = "",
        @SerializedName("avatar_url")
        var avatarUrl: String = "",
        @SerializedName("name")
        var name: String = "",
        @SerializedName("company")
        var company: String = "",
        @SerializedName("repos_url")
        var reposUrl: String = "",
        @SerializedName("blog")
        var blog: String = ""
)