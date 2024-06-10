package com.example.roulettegame.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.roulettegame.domain.model.Stake
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Entity
@Parcelize
data class StakeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: LocalDateTime,
    val stake: Int,
    val color: String,
    val isAWinningBet: Boolean,
    val winningAmount: Int?

): Parcelable {
    fun toStake() : Stake {
        return Stake(
            date = date,
            stake = stake,
            color = color,
            isAWinningBet = isAWinningBet,
            winningAmount = winningAmount
        )
    }
}
