package com.snappymob.kotlincomponents.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.snappymob.kotlincomponents.model.Repo
import io.reactivex.Flowable

@Dao
abstract class RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg repos: Repo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repositories: List<Repo>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createRepoIfNotExists(repo: Repo): Long

    @Query("SELECT * FROM repo WHERE owner_login = :arg0 AND name = :arg1")
    abstract fun load(login: String, name: String): Flowable<Repo>

    @Query("SELECT * FROM Repo "
            + "WHERE owner_login = :arg0 "
            + "ORDER BY stars DESC")
    abstract fun loadRepositories(owner: String): Flowable<List<Repo>>

    @Query("SELECT * FROM Repo WHERE id in (:arg0)")
    protected abstract fun loadById(repoIds: List<Int>): Flowable<List<Repo>>

}