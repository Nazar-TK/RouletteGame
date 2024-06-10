package com.example.roulettegame.data.repository

import android.util.Log
import com.example.roulettegame.core.utils.Resource
import com.example.roulettegame.data.RouletteDao
import com.example.roulettegame.domain.model.UserData
import com.example.roulettegame.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserDataRepositoryImpl(private val dao: RouletteDao): UserDataRepository {
    override fun insertUserData(userData: UserData): Flow<Resource<Boolean>> = flow {
        try {
            Log.d("HERE", " UserDataRepositoryImpl insertUserData")
            dao.insertUserData(userData.toUserDataEntity())
            Log.d("HERE", " UserDataRepositoryImpl insertUserData SUCCESS")
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Could not insert user data to database."))
        }
    }

    override fun getUserData(): Flow<Resource<UserData>> = flow {
        try {
            val userData = dao.getUserData().toUserData()
            emit(Resource.Success(userData))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Could not get user data from database."))
        }
    }
}