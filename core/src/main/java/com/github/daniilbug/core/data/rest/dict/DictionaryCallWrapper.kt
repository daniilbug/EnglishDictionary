@file:Suppress("UNCHECKED_CAST")

package com.github.daniilbug.core.data.rest.dict

import com.github.daniilbug.core.core.result.BinaryResult
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

class DictionaryCallWrapper<F, T>(
    private val delegate: Call<T>
) : Call<BinaryResult<DictionaryError, F>> {

    override fun cancel() = delegate.cancel()

    override fun clone(): Call<BinaryResult<DictionaryError, F>> {
        return DictionaryCallWrapper<F, T>(delegate.clone())
    }

    override fun execute(): Response<BinaryResult<DictionaryError, F>> {
        return wrapResponse(delegate.execute())
    }

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

    override fun enqueue(callback: Callback<BinaryResult<DictionaryError, F>>) {
        return try {
            delegate.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    callback.onResponse(this@DictionaryCallWrapper, wrapResponse(response))
                }

                override fun onFailure(call: Call<T>, throwable: Throwable) {
                    throwable.printStackTrace()
                    val errorReason = when (throwable) {
                        is IOException -> DictionaryError.ConnectionError(throwable)
                        else -> DictionaryError.UnexpectedError(throwable)
                    }
                    val response = Response.success(
                        BinaryResult.Error(errorReason) as BinaryResult<DictionaryError, F>
                    )
                    callback.onResponse(this@DictionaryCallWrapper, response)
                }
            })
        } catch (ex: Exception) {
            ex.printStackTrace()
            val unexpectedError = BinaryResult.Error(DictionaryError.UnexpectedError(ex))
            val response = Response.success(unexpectedError as BinaryResult<DictionaryError, F>)
            callback.onResponse(this, response)
        }
    }

    private fun wrapResponse(response: Response<T>): Response<BinaryResult<DictionaryError, F>> {
        return try {
            if (response.isSuccessful) {
                Response.success(BinaryResult.Success(response.body()) as BinaryResult<DictionaryError, F>)
            } else {
                val reason = createServerErrorReason(response)
                Response.success(BinaryResult.Error(reason))
            }
        } catch (e: Exception) {
            Response.success(BinaryResult.Error(DictionaryError.UnexpectedError(e)))
        }
    }

    private fun createServerErrorReason(response: Response<T>): DictionaryError {
        return when (response.code()) {
            404 -> DictionaryError.NotFound
            else -> DictionaryError.UnexpectedError(
                IllegalStateException("Unexpected Server Error: ${response.code()}")
            )
        }
    }
}
