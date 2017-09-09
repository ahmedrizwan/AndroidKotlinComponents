package com.snappymob.kotlincomponents.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index

import com.google.gson.annotations.SerializedName

@Entity(indices = arrayOf(Index("id"),
        Index("owner_login")),
        primaryKeys = arrayOf("name", "owner_login"))
data class Repo(
        val id: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("owner")
        @Embedded(prefix = "owner_")
        val owner: Owner,
        @SerializedName("stargazers_count")
        val stars: Int
) {

    data class Owner(
            @SerializedName("login")
            val login: String?,
            @SerializedName("url")
            val url: String?
    )

    companion object {
        val UNKNOWN_ID = -1
    }
}