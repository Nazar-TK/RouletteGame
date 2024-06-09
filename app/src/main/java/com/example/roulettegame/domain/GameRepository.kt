package com.example.roulettegame.domain

import kotlinx.coroutines.flow.Flow

interface GameRepository {

    suspend fun insertStake()

    fun getStakes() : Flow<List<Stake>>
}