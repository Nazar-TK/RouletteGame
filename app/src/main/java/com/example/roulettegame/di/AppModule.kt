package com.example.roulettegame.di

import com.example.roulettegame.data.GameRepositoryImpl
import com.example.roulettegame.domain.GameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoRepository(): GameRepository {
        return GameRepositoryImpl()
    }
}