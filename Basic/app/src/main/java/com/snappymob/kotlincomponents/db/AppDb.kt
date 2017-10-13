package com.snappymob.kotlincomponents.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.snappymob.kotlincomponents.model.Repo

/**
 * Created by ahmedrizwan on 9/9/17.
 */

@Database(entities = arrayOf(Repo::class), version = 2, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}
