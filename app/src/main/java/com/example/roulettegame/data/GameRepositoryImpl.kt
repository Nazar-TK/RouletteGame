package com.example.roulettegame.data

import android.util.Log
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

    override suspend fun rollTheRoulette(
        stake: Int,
        color: String,
        rotationValue: Float,
    ) : String {

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
        Log.d("HERE! currentSector: ", currentSector.toString())
        return sectorColor
    }
}