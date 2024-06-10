package com.example.roulettegame.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.roulettegame.domain.model.UserData

@Entity
data class UserDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val coins: Int,
    val roulette: Int
){
    fun toUserData() : UserData {
        return UserData(
            coins = coins,
            roulette = roulette
        )
    }
}