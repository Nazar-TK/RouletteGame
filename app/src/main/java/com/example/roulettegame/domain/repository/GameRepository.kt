package com.example.roulettegame.domain.repository

import com.example.roulettegame.core.utils.Resource
import com.example.roulettegame.domain.model.Stake
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    fun insertStake(stake: Stake): Flow<Resource<Pair<Boolean, Stake>>>

    fun getStakes() : Flow<Resource<List<Stake>>>

    fun rollTheRoulette(stake: Int, color: String, rotationValue: Float) : Flow<Resource<Pair<Boolean, Stake>>>
}