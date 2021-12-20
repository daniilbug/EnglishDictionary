package com.github.daniilbug.core.domain.source.impl

import com.github.daniilbug.core.data.local.SearchEntity
import com.github.daniilbug.core.data.local.SearchHistoryDao
import com.github.daniilbug.core.domain.mapper.asDomain
import com.github.daniilbug.core.domain.mapper.asEntity
import com.github.daniilbug.core.domain.model.SearchItemDomain
import com.github.daniilbug.core.domain.source.SearchHistoryLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomSearchHistoryLocalDataSource @Inject constructor(
    private val dao: SearchHistoryDao
): SearchHistoryLocalDataSource {

    companion object {
        const val HISTORY_LIMIT = 10
    }

    override fun getSearchHistory(): Flow<List<SearchItemDomain>> {
        return dao.getAll().map { entities ->
            entities.map(SearchEntity::asDomain)
        }
    }

    override suspend fun addSearchItem(item: SearchItemDomain) {
        dao.addWithLimit(item.asEntity(), limit = HISTORY_LIMIT)
    }
}