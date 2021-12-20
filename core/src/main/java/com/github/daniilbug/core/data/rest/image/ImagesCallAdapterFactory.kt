package com.github.daniilbug.core.data.rest.image

import com.github.daniilbug.core.core.result.BinaryResult
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject

class ImagesCallAdapterFactory @Inject constructor() : CallAdapter.Factory() {

    private class ResultCallAdapter<R, T>(
        private val resultType: Type
        ) : CallAdapter<R, Call<BinaryResult<ImagesError, T>>> {

        override fun adapt(call: Call<R>): Call<BinaryResult<ImagesError, T>> {
            return ImagesCallWrapper(call)
        }

        override fun responseType() = resultType
    }

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<out Any, Call<BinaryResult<ImagesError, *>>>? {
        if (returnType !is ParameterizedType) {
            return null
        }

        val containerType = getParameterUpperBound(0, returnType)

        if (getRawType(containerType) != BinaryResult::class.java) {
            return null
        }

        if (containerType !is ParameterizedType) {
            return null
        }

        val errorType = getParameterUpperBound(0, containerType)
        if (getRawType(errorType) != ImagesError::class.java) {
            return null
        }

        val resultType = getParameterUpperBound(1, containerType)
        return ResultCallAdapter(resultType)
    }
}
