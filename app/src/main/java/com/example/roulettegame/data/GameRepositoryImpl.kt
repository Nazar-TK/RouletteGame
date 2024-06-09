package com.example.roulettegame.data

import com.example.roulettegame.domain.GameRepository
import com.example.roulettegame.domain.Stake
import kotlinx.coroutines.flow.Flow

class GameRepositoryImpl: GameRepository {
    override suspend fun insertStake() {
        TODO("Not yet implemented")
    }

    override fun getStakes(): Flow<List<Stake>> {
        TODO("Not yet implemented")
    }
}