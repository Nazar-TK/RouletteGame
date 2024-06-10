package com.example.roulettegame.domain.model

import com.example.roulettegame.data.entity.UserDataEntity

data class UserData(
    val coins: Int,
    val roulette: Int
) {
    fun toUserDataEntity(): UserDataEntity {
        return UserDataEntity(
            coins = coins,
            roulette = roulette
        )
    }
}
