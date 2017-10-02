package com.snappymob.kotlincomponents.db

import android.arch.persistence.room.*
import com.snappymob.kotlincomponents.model.Repo
import io.reactivex.Flowable

@Dao
abstract class RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg repos: Repo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repositories: List<Repo>):List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createRepoIfNotExists(repo: Repo): Long

    @Update
    abstract fun updateRepos(repositories: List<Repo>)

    @Query("SELECT * FROM repo WHERE owner_login = :arg0 AND name = :arg1")
    abstract fun load(login: String, name: String): Flowable<Repo>

    @Query("SELECT * FROM Repo "
            + "WHERE lower(owner_login) = lower(:arg0)"
            + "ORDER BY stars DESC")
    abstract fun loadRepositories(owner: String): Flowable<List<Repo>>

    @Query("SELECT * FROM Repo WHERE id in (:arg0)")
    protected abstract fun loadById(repoIds: List<Int>): Flowable<List<Repo>>

    @Query("DELETE FROM Repo")
    abstract fun clearAll()

}