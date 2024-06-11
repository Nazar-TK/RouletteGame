package com.example.roulettegame.domain.model

import com.example.roulettegame.data.entity.UserDataEntity

data class UserData(
    val coins: Int,
) {
    fun toUserDataEntity(): UserDataEntity {
        return UserDataEntity(
            coins = coins,
        )
    }
}
