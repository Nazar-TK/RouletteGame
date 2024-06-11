package com.example.roulettegame.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.roulettegame.domain.model.UserData

@Entity
data class UserDataEntity(
    @PrimaryKey
    val id: Int = 1,
    val coins: Int,
){
    fun toUserData() : UserData {
        return UserData(
            coins = coins
        )
    }
}