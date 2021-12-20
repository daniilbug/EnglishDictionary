package com.github.daniilbug.core.di.network

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .build()

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor())
        .build()
}