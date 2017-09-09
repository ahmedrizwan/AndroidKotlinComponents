package com.snappymob.kotlincomponents.model

import android.arch.persistence.room.Entity

import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = arrayOf("login"))
data class User(
        @SerializedName("login")
        var login: String,
        @SerializedName("avatar_url")
        var avatarUrl: String,
        @SerializedName("name")
        var name: String,
        @SerializedName("company")
        var company: String,
        @SerializedName("repos_url")
        var reposUrl: String,
        @SerializedName("blog")
        var blog: String
)