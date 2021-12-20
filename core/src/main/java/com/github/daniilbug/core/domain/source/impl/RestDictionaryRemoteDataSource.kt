package com.github.daniilbug.core.domain.source.impl

import com.github.daniilbug.core.core.result.BinaryResult
import com.github.daniilbug.core.core.result.mapData
import com.github.daniilbug.core.data.rest.DictionaryApi
import com.github.daniilbug.core.data.rest.DictionaryError
import com.github.daniilbug.core.domain.mapper.asDomainDefinitions
import com.github.daniilbug.core.domain.model.DefinitionDomain
import com.github.daniilbug.core.domain.source.DictionaryRemoteDataSource
import javax.inject.Inject

class RestDictionaryRemoteDataSource @Inject constructor(
    private val dictionaryApi: DictionaryApi
) : DictionaryRemoteDataSource {

    override suspend fun getDefinitions(word: String): BinaryResult<DictionaryError, List<DefinitionDomain>> {
        return dictionaryApi.getDefinitions()
            .mapData { definitions -> definitions.asDomainDefinitions() }
    }
}