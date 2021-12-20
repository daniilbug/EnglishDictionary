package com.github.daniilbug.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@Dao
abstract class SearchHistoryDao {
    @Query("SELECT * FROM SearchEntity")
    abstract fun getAll(): Flow<List<SearchEntity>>

    @Query("SELECT COUNT(*) FROM SearchEntity")
    abstract fun count(): Flow<Int>

    @Insert
    abstract suspend fun insertAll(vararg searchEntities: SearchEntity)

    @Query("DELETE FROM SearchEntity WHERE timestamp = (SELECT MIN(timestamp) FROM SearchEntity)")
    abstract suspend fun deleteOldest()

    @Transaction
    open suspend fun addWithLimit(
        searchEntity: SearchEntity,
        limit: Int
    ) {
        val count = count().first()
        if (count > limit) {
            repeat(count - limit + 1) {
                deleteOldest()
            }
        }
        insertAll(searchEntity)
    }
}