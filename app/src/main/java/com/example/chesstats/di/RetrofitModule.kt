package com.example.chesstats.di

import com.example.chesstats.data.network.services.PlayerService
import com.example.chesstats.data.repositories.ChessRepositoryImp
import com.example.chesstats.domain.repositories.ChessRepository
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
object RetrofitModule {

    private const val BASE_URL = "https://api.chess.com/"

    @Provides
    @Singleton
    @Named("ChessAPI")
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePlayerService(@Named("ChessAPI") retrofit: Retrofit): PlayerService {
        return retrofit.create(PlayerService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(playerService: PlayerService): ChessRepository{
        return ChessRepositoryImp(playerService)
    }
}