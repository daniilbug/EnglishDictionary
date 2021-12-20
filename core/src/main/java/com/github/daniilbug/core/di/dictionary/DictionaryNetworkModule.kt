package com.github.daniilbug.core.di.dictionary

import com.github.daniilbug.core.data.rest.DictionaryApi
import com.github.daniilbug.core.data.rest.DictionaryCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
object DictionaryNetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(DictionaryApi.BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(DictionaryCallAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Singleton
    @Provides
    fun provideDictionaryApi(retrofit: Retrofit): DictionaryApi = retrofit.create()
}