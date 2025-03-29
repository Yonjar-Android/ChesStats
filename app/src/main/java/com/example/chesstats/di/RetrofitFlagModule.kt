package com.example.chesstats.di

import com.example.chesstats.data.network.services.FlagService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitFlagModule {

    private const val BASE_URL = "https://restcountries.com/v3.1/"

    @Provides
    @Singleton
    @Named("FlagAPI")
    fun provideFlagsRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideFlagService(@Named("FlagAPI") retrofit: Retrofit): FlagService {
        return retrofit.create(FlagService::class.java)
    }

}