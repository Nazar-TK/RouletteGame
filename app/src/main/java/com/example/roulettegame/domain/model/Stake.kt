package com.example.roulettegame.domain.model

import com.example.roulettegame.data.entity.StakeEntity
import java.time.LocalDateTime

data class Stake(
    val date: LocalDateTime,
    val stake: Int,
    val color: String,
    val isAWinningBet: Boolean,
    val winningAmount: Int?
){
    fun toStakeEntity(): StakeEntity {
        return StakeEntity(
            date = date,
            stake = stake,
            color = color,
            isAWinningBet = isAWinningBet,
            winningAmount = winningAmount
        )
    }
}
