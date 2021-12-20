package com.github.daniilbug.core.domain.source

import com.github.daniilbug.core.domain.model.SearchItemDomain
import kotlinx.coroutines.flow.Flow

interface SearchHistoryLocalDataSource {
    fun getSearchHistory(): Flow<List<SearchItemDomain>>

    suspend fun addSearchItem(item: SearchItemDomain)
}