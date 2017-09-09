package com.snappymob.kotlincomponents.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.snappymob.kotlincomponents.model.Repo
import com.snappymob.kotlincomponents.model.User

/**
 * Created by ahmedrizwan on 9/9/17.
 */

@Database(entities = arrayOf(User::class, Repo::class), version = 1)
abstract class GithubDb : RoomDatabase() {
//    abstract fun userDao(): UserDao

//    abstract fun repoDao(): RepoDao
}
