package com.github.daniilbug.core.domain.source.impl

import android.util.Log
import com.github.daniilbug.core.core.result.BinaryResult
import com.github.daniilbug.core.core.result.mapData
import com.github.daniilbug.core.core.result.orNull
import com.github.daniilbug.core.data.rest.dict.DictionaryApi
import com.github.daniilbug.core.data.rest.dict.DictionaryError
import com.github.daniilbug.core.data.rest.image.ImagesApi
import com.github.daniilbug.core.domain.mapper.asDomainDefinitions
import com.github.daniilbug.core.domain.model.DefinitionDomain
import com.github.daniilbug.core.domain.model.DictionaryAnswerDomain
import com.github.daniilbug.core.domain.source.DictionaryRemoteDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class RestDictionaryRemoteDataSource @Inject constructor(
    private val dictionaryApi: DictionaryApi,
    private val imagesApi: ImagesApi
) : DictionaryRemoteDataSource {

    override suspend fun getDefinitions(
        word: String
    ): BinaryResult<DictionaryError, DictionaryAnswerDomain> = coroutineScope {
        val imagesDeferred = async { imagesApi.getImagesByWord(word) }
        val definitionsDeferred = async { dictionaryApi.getDefinitions(word) }

        val images = imagesDeferred.await().orNull()
        definitionsDeferred.await().mapData { answers ->
            DictionaryAnswerDomain(
                images?.data?.firstOrNull()?.imageUrl,
                definitions = answers.asDomainDefinitions()
            )
        }
    }
}