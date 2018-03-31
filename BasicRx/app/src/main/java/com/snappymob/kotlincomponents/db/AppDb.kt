package com.snappymob.kotlincomponents.db

import android.arch.persistence.room.*
import com.snappymob.kotlincomponents.model.Repo
import io.reactivex.Flowable

/**
 * Created by ahmedrizwan on 9/9/17.
 * Database Class including the Dao
 * TODO: Change the database & dao based on what you want
 */
@Database(entities = arrayOf(Repo::class), version = 2, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}

@Dao
abstract class RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repositories: List<Repo>)

    @Query("SELECT * FROM Repo "
            + "WHERE lower(owner_login) = lower(:owner)"
            + "ORDER BY stars DESC")
    abstract fun loadRepositories(owner: String): Flowable<List<Repo>>

}