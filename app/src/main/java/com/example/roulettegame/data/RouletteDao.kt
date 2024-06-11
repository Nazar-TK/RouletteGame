package com.example.roulettegame.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roulettegame.data.entity.StakeEntity
import com.example.roulettegame.data.entity.UserDataEntity

@Dao
interface RouletteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStake(stake: StakeEntity)

    @Query("SELECT * FROM stakeentity ORDER BY date DESC LIMIT 50")
    suspend fun getStakes() : List<StakeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(userData: UserDataEntity)

    @Query("SELECT * FROM userdataentity LIMIT 1")
    suspend fun getUserData() : UserDataEntity
}