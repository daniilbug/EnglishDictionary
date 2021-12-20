package com.github.daniilbug.core.di.dictionary

import com.github.daniilbug.core.data.rest.image.ImagesApi
import com.github.daniilbug.core.data.rest.image.ImagesCallAdapterFactory
import com.github.daniilbug.core.di.ImagesRetrofit
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
object ImagesNetworkModule {

    @Singleton
    @ImagesRetrofit
    @Provides
    fun provideRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(ImagesApi.BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(ImagesCallAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Singleton
    @Provides
    fun provideImagesApi(
        @ImagesRetrofit retrofit: Retrofit
    ): ImagesApi = retrofit.create()
}