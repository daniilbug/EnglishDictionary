package com.github.daniilbug.core.domain.mapper

import com.github.daniilbug.core.data.local.SearchEntity
import com.github.daniilbug.core.domain.model.SearchItemDomain

fun SearchEntity.asDomain(): SearchItemDomain {
    return SearchItemDomain(word, firstDefinition)
}

fun SearchItemDomain.asEntity(): SearchEntity {
    return SearchEntity(word, firstDefinition, System.currentTimeMillis())
}