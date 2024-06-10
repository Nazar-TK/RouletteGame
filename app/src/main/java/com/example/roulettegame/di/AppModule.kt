package com.example.roulettegame.di

import android.app.Application
import androidx.room.Room
import com.example.roulettegame.data.Converters
import com.example.roulettegame.data.RouletteDatabase
import com.example.roulettegame.data.repository.GameRepositoryImpl
import com.example.roulettegame.data.repository.UserDataRepositoryImpl
import com.example.roulettegame.domain.repository.GameRepository
import com.example.roulettegame.domain.repository.UserDataRepository
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
    fun provideWordInfoDatabase(app: Application): RouletteDatabase {
        return Room.databaseBuilder(
            app,
            RouletteDatabase::class.java,
            "roulette_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: RouletteDatabase): GameRepository {
        return GameRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideUserDataRepository(db: RouletteDatabase): UserDataRepository {
        return UserDataRepositoryImpl(db.dao)
    }
}