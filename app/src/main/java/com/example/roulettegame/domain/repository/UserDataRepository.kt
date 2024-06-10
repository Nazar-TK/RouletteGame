package com.example.roulettegame.domain.repository

import com.example.roulettegame.core.utils.Resource
import com.example.roulettegame.domain.model.Stake
import com.example.roulettegame.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    fun insertUserData(userData: UserData): Flow<Resource<Boolean>>

    fun getUserData() : Flow<Resource<UserData>>
}