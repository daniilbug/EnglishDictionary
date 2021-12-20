@file:Suppress("UNCHECKED_CAST")

package com.github.daniilbug.core.data.rest.image

import com.github.daniilbug.core.core.result.BinaryResult
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

class ImagesCallWrapper<F, T>(
    private val delegate: Call<T>
) : Call<BinaryResult<ImagesError, F>> {

    override fun cancel() = delegate.cancel()

    override fun clone(): Call<BinaryResult<ImagesError, F>> {
        return ImagesCallWrapper<F, T>(delegate.clone())
    }

    override fun execute(): Response<BinaryResult<ImagesError, F>> {
        return wrapResponse(delegate.execute())
    }

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

    override fun enqueue(callback: Callback<BinaryResult<ImagesError, F>>) {
        return try {
            delegate.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    callback.onResponse(this@ImagesCallWrapper, wrapResponse(response))
                }

                override fun onFailure(call: Call<T>, throwable: Throwable) {
                    val errorReason = when (throwable) {
                        is IOException -> ImagesError.ConnectionError(throwable)
                        else -> ImagesError.UnexpectedError(throwable)
                    }
                    val response = Response.success(
                        BinaryResult.Error(errorReason) as BinaryResult<ImagesError, F>
                    )
                    callback.onResponse(this@ImagesCallWrapper, response)
                }
            })
        } catch (ex: Exception) {
            val unexpectedError = BinaryResult.Error(ImagesError.UnexpectedError(ex))
            val response = Response.success(unexpectedError as BinaryResult<ImagesError, F>)
            callback.onResponse(this, response)
        }
    }

    private fun wrapResponse(response: Response<T>): Response<BinaryResult<ImagesError, F>> {
        return try {
            if (response.isSuccessful) {
                Response.success(BinaryResult.Success(response.body()) as BinaryResult<ImagesError, F>)
            } else {
                val reason = createServerErrorReason(response)
                Response.success(BinaryResult.Error(reason))
            }
        } catch (e: Exception) {
            Response.success(BinaryResult.Error(ImagesError.UnexpectedError(e)))
        }
    }

    private fun createServerErrorReason(response: Response<T>): ImagesError {
        return ImagesError.UnexpectedError(
            IllegalStateException("Unexpected Server Error: ${response.code()}")
        )
    }
}
