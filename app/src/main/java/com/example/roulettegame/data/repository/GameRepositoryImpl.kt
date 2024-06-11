package com.example.roulettegame.data.repository

import android.util.Log
import com.example.roulettegame.core.utils.Resource
import com.example.roulettegame.data.RouletteDao
import com.example.roulettegame.domain.repository.GameRepository
import com.example.roulettegame.domain.model.Stake
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime

class GameRepositoryImpl(private val dao: RouletteDao): GameRepository {
    override fun insertStake(stake: Stake): Flow<Resource<Pair<Boolean, Stake>>> = flow {
        try {
            dao.insertStake(stake.toStakeEntity())
            emit(Resource.Success(Pair(true, stake)))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Could not insert stake data to database."))
        }
    }

    override fun getStakes(): Flow<Resource<List<Stake>>>  = flow {
        try {
            val stakes = dao.getStakes().map { it.toStake() }
            emit(Resource.Success(stakes))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Could not get stakes data from database."))
        }
    }

    override fun rollTheRoulette(
        stake: Int,
        color: String,
        rotationValue: Float,
    ) : Flow<Resource<Pair<Boolean, Stake>>> {

        val angle = rotationValue % 360f
        val sectorAngleRange = 360.0 / 37.0f
        val currentSector = kotlin.math.ceil((angle + (sectorAngleRange / 2)) / sectorAngleRange).toInt()

        val sectorColor = if (currentSector == 1) {
            "Green"
        } else if (currentSector % 2 == 0) {
            "Black"
        } else {
            "Red"
        }
        val stakeData = processTheData(stake, color, sectorColor)

        return insertStake(stakeData)
    }

    private fun processTheData(stake: Int, color: String, sectorColor: String) : Stake {

        val date = LocalDateTime.now()
        val isAWinningBet = color == sectorColor
        var winningAmount: Int? = null
        if( isAWinningBet ) {
            winningAmount = if( color == "Green" )
                stake * 35
            else
                stake
        }

        return Stake(
            date = date,
            stake = stake,
            color = color,
            isAWinningBet = isAWinningBet,
            winningAmount = winningAmount
        )
    }
}